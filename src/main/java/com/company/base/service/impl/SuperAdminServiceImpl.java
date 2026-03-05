package com.company.base.service.impl;

import com.company.base.dto.request.admin.LandlordSubscriptionRequest;
import com.company.base.dto.request.admin.SubscriptionPlanRequest;
import com.company.base.dto.request.admin.SupportTicketRequest;
import com.company.base.dto.request.admin.SupportTicketUpdateRequest;
import com.company.base.dto.request.admin.SystemConfigRequest;
import com.company.base.dto.response.admin.LandlordProfileResponse;
import com.company.base.dto.response.admin.LandlordSubscriptionResponse;
import com.company.base.dto.response.admin.SubscriptionPlanResponse;
import com.company.base.dto.response.admin.SuperAdminDashboardResponse;
import com.company.base.dto.response.admin.SupportTicketResponse;
import com.company.base.dto.response.admin.SystemConfigResponse;
import com.company.base.entity.LandlordProfile;
import com.company.base.entity.LandlordSubscription;
import com.company.base.entity.SubscriptionPlan;
import com.company.base.entity.SupportTicket;
import com.company.base.entity.SystemConfig;
import com.company.base.entity.User;
import com.company.base.exception.AppException;
import com.company.base.repository.admin.LandlordProfileRepository;
import com.company.base.repository.admin.LandlordSubscriptionRepository;
import com.company.base.repository.admin.SubscriptionPlanRepository;
import com.company.base.repository.admin.SupportTicketRepository;
import com.company.base.repository.admin.SystemConfigRepository;
import com.company.base.repository.admin.UserRepository;
import com.company.base.service.SuperAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Service implementation containing business logic for this module.
 */
@Service
@RequiredArgsConstructor
public class SuperAdminServiceImpl implements SuperAdminService {

    private final LandlordProfileRepository landlordProfileRepository;
    private final LandlordSubscriptionRepository landlordSubscriptionRepository;
    private final SubscriptionPlanRepository subscriptionPlanRepository;
    private final SystemConfigRepository systemConfigRepository;
    private final SupportTicketRepository supportTicketRepository;
    private final UserRepository userRepository;

    @Override
    public SuperAdminDashboardResponse getDashboard(Integer fromMonth, Integer fromYear, Integer toMonth, Integer toYear) {
        MonthRange range = resolveRange(fromMonth, fromYear, toMonth, toYear);
        List<LandlordProfile> activeLandlords = landlordProfileRepository.findByStatusIgnoreCaseOrderByCreatedAtDesc("APPROVED");

        BigDecimal totalRevenue = landlordSubscriptionRepository.findAll().stream()
                .map(LandlordSubscription::getAmountPaid)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        List<SuperAdminDashboardResponse.UserGrowthPoint> growth = new ArrayList<>();
        for (YearMonth ym : range.months) {
            long count = activeLandlords.stream()
                    .filter(p -> p.getCreatedAt() != null)
                    .filter(p -> p.getCreatedAt().getYear() == ym.getYear() && p.getCreatedAt().getMonthValue() == ym.getMonthValue())
                    .count();
            growth.add(SuperAdminDashboardResponse.UserGrowthPoint.builder()
                    .month(ym.getMonthValue())
                    .year(ym.getYear())
                    .newUsers(count)
                    .build());
        }

        Runtime runtime = Runtime.getRuntime();
        long maxMem = runtime.maxMemory();
        long usedMem = runtime.totalMemory() - runtime.freeMemory();
        double percent = maxMem == 0 ? 0.0 : (usedMem * 100.0) / maxMem;
        String serverStatus = percent >= 90 ? "CRITICAL" : percent >= 75 ? "WARNING" : "HEALTHY";

        return SuperAdminDashboardResponse.builder()
                .totalLandlords((long) activeLandlords.size())
                .totalSubscriptionRevenue(totalRevenue)
                .newUserGrowth(growth)
                .serverStatus(SuperAdminDashboardResponse.ServerStatus.builder()
                        .memoryUsedMb(toMb(usedMem))
                        .memoryMaxMb(toMb(maxMem))
                        .memoryUsagePercent(percent)
                        .status(serverStatus)
                        .build())
                .build();
    }

    @Override
    public List<LandlordProfileResponse> getLandlords(String status) {
        List<LandlordProfile> profiles = (status == null || status.isBlank())
                ? landlordProfileRepository.findAllByOrderByCreatedAtDesc()
                : landlordProfileRepository.findByStatusIgnoreCaseOrderByCreatedAtDesc(status);
        return toLandlordResponses(profiles);
    }

    @Override
    public LandlordProfileResponse approveLandlord(Long landlordProfileId) {
        LandlordProfile profile = getLandlordProfile(landlordProfileId);
        User user = getUser(profile.getUserId());
        profile.setStatus("APPROVED");
        user.setEnabled(Boolean.TRUE);
        landlordProfileRepository.save(profile);
        userRepository.save(user);
        return toLandlordResponse(profile, user);
    }

    @Override
    public LandlordProfileResponse lockLandlord(Long landlordProfileId, String note) {
        LandlordProfile profile = getLandlordProfile(landlordProfileId);
        User user = getUser(profile.getUserId());
        profile.setStatus("LOCKED");
        profile.setNote(note);
        user.setEnabled(Boolean.FALSE);
        landlordProfileRepository.save(profile);
        userRepository.save(user);
        return toLandlordResponse(profile, user);
    }

    @Override
    public LandlordProfileResponse unlockLandlord(Long landlordProfileId) {
        LandlordProfile profile = getLandlordProfile(landlordProfileId);
        User user = getUser(profile.getUserId());
        profile.setStatus("APPROVED");
        user.setEnabled(Boolean.TRUE);
        landlordProfileRepository.save(profile);
        userRepository.save(user);
        return toLandlordResponse(profile, user);
    }

    @Override
    public SubscriptionPlanResponse createPlan(SubscriptionPlanRequest request) {
        SubscriptionPlan plan = new SubscriptionPlan();
        applyPlanUpdate(plan, request);
        return toPlanResponse(subscriptionPlanRepository.save(plan));
    }

    @Override
    public SubscriptionPlanResponse updatePlan(Long planId, SubscriptionPlanRequest request) {
        SubscriptionPlan plan = getPlan(planId);
        applyPlanUpdate(plan, request);
        return toPlanResponse(subscriptionPlanRepository.save(plan));
    }

    @Override
    public List<SubscriptionPlanResponse> getPlans() {
        return subscriptionPlanRepository.findAllByOrderByMonthlyPriceAsc()
                .stream()
                .map(this::toPlanResponse)
                .toList();
    }

    @Override
    public void deletePlan(Long planId) {
        if (!subscriptionPlanRepository.existsById(planId)) {
            throw new AppException(HttpStatus.NOT_FOUND.value(), "Plan not found");
        }
        subscriptionPlanRepository.deleteById(planId);
    }

    @Override
    public LandlordSubscriptionResponse createSubscription(LandlordSubscriptionRequest request) {
        getLandlordProfile(request.getLandlordProfileId());
        getPlan(request.getPlanId());
        LandlordSubscription sub = new LandlordSubscription();
        sub.setLandlordProfileId(request.getLandlordProfileId());
        sub.setPlanId(request.getPlanId());
        sub.setStartDate(request.getStartDate());
        sub.setEndDate(request.getEndDate());
        sub.setStatus(normalize(request.getStatus()));
        sub.setAmountPaid(request.getAmountPaid());
        return toSubscriptionResponse(landlordSubscriptionRepository.save(sub));
    }

    @Override
    public List<LandlordSubscriptionResponse> getSubscriptions(Long landlordProfileId) {
        return landlordSubscriptionRepository.findByLandlordProfileIdOrderByStartDateDescIdDesc(landlordProfileId)
                .stream()
                .map(this::toSubscriptionResponse)
                .toList();
    }

    @Override
    public SystemConfigResponse upsertSystemConfig(SystemConfigRequest request) {
        SystemConfig config = systemConfigRepository.findByConfigKey(request.getConfigKey())
                .orElseGet(SystemConfig::new);
        config.setConfigKey(request.getConfigKey());
        config.setConfigValue(request.getConfigValue());
        config.setDescription(request.getDescription());
        return toConfigResponse(systemConfigRepository.save(config));
    }

    @Override
    public List<SystemConfigResponse> getSystemConfigs() {
        return systemConfigRepository.findAllByOrderByConfigKeyAsc()
                .stream()
                .map(this::toConfigResponse)
                .toList();
    }

    @Override
    public SupportTicketResponse createSupportTicket(SupportTicketRequest request) {
        getLandlordProfile(request.getLandlordProfileId());
        SupportTicket ticket = new SupportTicket();
        ticket.setLandlordProfileId(request.getLandlordProfileId());
        ticket.setTitle(request.getTitle());
        ticket.setDescription(request.getDescription());
        ticket.setPriority(normalize(request.getPriority()));
        ticket.setStatus("OPEN");
        return toTicketResponse(supportTicketRepository.save(ticket));
    }

    @Override
    public SupportTicketResponse updateSupportTicket(Long ticketId, SupportTicketUpdateRequest request) {
        SupportTicket ticket = supportTicketRepository.findById(ticketId)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND.value(), "Ticket not found"));
        if (request.getStatus() != null) {
            ticket.setStatus(normalize(request.getStatus()));
        }
        if (request.getAssignedTo() != null) {
            ticket.setAssignedTo(request.getAssignedTo());
        }
        if (request.getResolutionNote() != null) {
            ticket.setResolutionNote(request.getResolutionNote());
        }
        return toTicketResponse(supportTicketRepository.save(ticket));
    }

    @Override
    public List<SupportTicketResponse> getSupportTickets(String status) {
        List<SupportTicket> tickets = (status == null || status.isBlank())
                ? supportTicketRepository.findAllByOrderByCreatedAtDesc()
                : supportTicketRepository.findByStatusIgnoreCaseOrderByCreatedAtDesc(status);
        return tickets.stream()
                .map(this::toTicketResponse)
                .toList();
    }

    private List<LandlordProfileResponse> toLandlordResponses(List<LandlordProfile> profiles) {
        Map<Long, User> users = userRepository.findAllById(
                profiles.stream().map(LandlordProfile::getUserId).filter(Objects::nonNull).toList()
        ).stream().collect(Collectors.toMap(User::getId, u -> u));
        return profiles.stream()
                .sorted(Comparator.comparing(LandlordProfile::getCreatedAt, Comparator.nullsLast(Comparator.reverseOrder())))
                .map(p -> toLandlordResponse(p, users.get(p.getUserId())))
                .toList();
    }

    private LandlordProfileResponse toLandlordResponse(LandlordProfile profile, User user) {
        return LandlordProfileResponse.builder()
                .id(profile.getId())
                .userId(profile.getUserId())
                .username(user != null ? user.getUsername() : null)
                .email(user != null ? user.getEmail() : null)
                .businessName(profile.getBusinessName())
                .contactPhone(profile.getContactPhone())
                .status(profile.getStatus())
                .accountEnabled(user != null ? user.getEnabled() : null)
                .createdAt(profile.getCreatedAt())
                .note(profile.getNote())
                .build();
    }

    private SubscriptionPlanResponse toPlanResponse(SubscriptionPlan plan) {
        return SubscriptionPlanResponse.builder()
                .id(plan.getId())
                .code(plan.getCode())
                .name(plan.getName())
                .maxRooms(plan.getMaxRooms())
                .monthlyPrice(plan.getMonthlyPrice())
                .active(plan.getActive())
                .description(plan.getDescription())
                .build();
    }

    private LandlordSubscriptionResponse toSubscriptionResponse(LandlordSubscription sub) {
        return LandlordSubscriptionResponse.builder()
                .id(sub.getId())
                .landlordProfileId(sub.getLandlordProfileId())
                .planId(sub.getPlanId())
                .startDate(sub.getStartDate())
                .endDate(sub.getEndDate())
                .status(sub.getStatus())
                .amountPaid(sub.getAmountPaid())
                .build();
    }

    private SystemConfigResponse toConfigResponse(SystemConfig config) {
        return SystemConfigResponse.builder()
                .id(config.getId())
                .configKey(config.getConfigKey())
                .configValue(config.getConfigValue())
                .description(config.getDescription())
                .build();
    }

    private SupportTicketResponse toTicketResponse(SupportTicket ticket) {
        return SupportTicketResponse.builder()
                .id(ticket.getId())
                .landlordProfileId(ticket.getLandlordProfileId())
                .title(ticket.getTitle())
                .description(ticket.getDescription())
                .status(ticket.getStatus())
                .priority(ticket.getPriority())
                .assignedTo(ticket.getAssignedTo())
                .resolutionNote(ticket.getResolutionNote())
                .createdAt(ticket.getCreatedAt())
                .updatedAt(ticket.getUpdatedAt())
                .build();
    }

    private LandlordProfile getLandlordProfile(Long id) {
        return landlordProfileRepository.findById(id)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND.value(), "Landlord profile not found"));
    }

    private User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND.value(), "User not found"));
    }

    private SubscriptionPlan getPlan(Long id) {
        return subscriptionPlanRepository.findById(id)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND.value(), "Subscription plan not found"));
    }

    private void applyPlanUpdate(SubscriptionPlan plan, SubscriptionPlanRequest request) {
        plan.setCode(normalize(request.getCode()));
        plan.setName(request.getName());
        plan.setMaxRooms(request.getMaxRooms());
        plan.setMonthlyPrice(request.getMonthlyPrice());
        plan.setActive(request.getActive() == null ? Boolean.TRUE : request.getActive());
        plan.setDescription(request.getDescription());
    }

    private String normalize(String value) {
        return value == null ? null : value.trim().toUpperCase();
    }

    private long toMb(long bytes) {
        return bytes / (1024L * 1024L);
    }

    private MonthRange resolveRange(Integer fromMonth, Integer fromYear, Integer toMonth, Integer toYear) {
        LocalDate now = LocalDate.now();
        YearMonth start = YearMonth.of(
                fromYear != null ? fromYear : now.getYear(),
                fromMonth != null ? fromMonth : Math.max(now.getMonthValue() - 5, 1)
        );
        YearMonth end = YearMonth.of(
                toYear != null ? toYear : now.getYear(),
                toMonth != null ? toMonth : now.getMonthValue()
        );

        if (end.isBefore(start)) {
            throw new AppException(HttpStatus.BAD_REQUEST.value(), "Invalid range: to date must be after from date");
        }

        List<YearMonth> months = new ArrayList<>();
        YearMonth current = start;
        while (!current.isAfter(end)) {
            months.add(current);
            current = current.plusMonths(1);
        }
        return new MonthRange(start, end, months);
    }

    private record MonthRange(YearMonth start, YearMonth end, List<YearMonth> months) {
    }
}
