package com.company.base.service.impl;

import com.company.base.dto.request.tenant.TemporaryResidenceRequest;
import com.company.base.dto.request.tenant.TenantMaintenanceRequest;
import com.company.base.dto.request.tenant.VehicleRegistrationRequest;
import com.company.base.dto.response.tenant.TenantDashboardResponse;
import com.company.base.dto.response.tenant.TenantInvoiceDetailResponse;
import com.company.base.dto.response.tenant.TenantInvoiceSummaryResponse;
import com.company.base.dto.response.tenant.TenantMaintenanceResponse;
import com.company.base.dto.response.tenant.TenantUtilityOverviewResponse;
import com.company.base.entity.Contract;
import com.company.base.entity.Invoice;
import com.company.base.entity.LandlordAnnouncement;
import com.company.base.entity.MaintenanceRequest;
import com.company.base.entity.OperationsDocument;
import com.company.base.entity.PaymentReceipt;
import com.company.base.entity.ServiceUsage;
import com.company.base.entity.Tenant;
import com.company.base.entity.TenantVehicleRegistration;
import com.company.base.exception.AppException;
import com.company.base.repository.host.ContractRepository;
import com.company.base.repository.host.InvoiceRepository;
import com.company.base.repository.host.MaintenanceRequestRepository;
import com.company.base.repository.host.OperationsDocumentRepository;
import com.company.base.repository.host.PaymentReceiptRepository;
import com.company.base.repository.host.ServiceRepository;
import com.company.base.repository.host.ServiceUsageRepository;
import com.company.base.repository.host.TenantRepository;
import com.company.base.repository.tenant.LandlordAnnouncementRepository;
import com.company.base.repository.tenant.TenantVehicleRegistrationRepository;
import com.company.base.service.TenantPortalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service implementation containing business logic for this module.
 */
@Service
@RequiredArgsConstructor
public class TenantPortalServiceImpl implements TenantPortalService {

    private static final Set<String> INVOICE_DEBT_STATUSES = Set.of("UNPAID", "OVERDUE");

    private final TenantRepository tenantRepository;
    private final ContractRepository contractRepository;
    private final InvoiceRepository invoiceRepository;
    private final PaymentReceiptRepository paymentReceiptRepository;
    private final ServiceUsageRepository serviceUsageRepository;
    private final ServiceRepository serviceRepository;
    private final MaintenanceRequestRepository maintenanceRequestRepository;
    private final OperationsDocumentRepository operationsDocumentRepository;
    private final LandlordAnnouncementRepository landlordAnnouncementRepository;
    private final TenantVehicleRegistrationRepository vehicleRegistrationRepository;

    @Override
    public TenantDashboardResponse getDashboard(String tenantId) {
        Tenant tenant = getTenant(tenantId);
        Contract activeContract = getActiveContract(tenantId);
        String roomId = activeContract != null ? activeContract.getRoomId() : null;
        List<Invoice> debtInvoices = getInvoicesByTenant(tenantId).stream()
                .filter(i -> i.getStatus() != null && INVOICE_DEBT_STATUSES.contains(i.getStatus().toUpperCase()))
                .toList();

        BigDecimal amountDue = debtInvoices.stream()
                .map(Invoice::getTotalAmount)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        LocalDate nearestDue = debtInvoices.stream()
                .map(Invoice::getDueDate)
                .filter(Objects::nonNull)
                .min(LocalDate::compareTo)
                .orElse(null);

        List<LandlordAnnouncement> announcements = roomId != null
                ? landlordAnnouncementRepository.findTop10ByRoomIdAndActiveTrueOrderByCreatedAtDescIdDesc(roomId)
                : landlordAnnouncementRepository.findTop10ByActiveTrueOrderByCreatedAtDescIdDesc();

        return TenantDashboardResponse.builder()
                .tenantId(tenantId)
                .roomId(roomId)
                .amountDueNow(amountDue)
                .nearestDueDate(nearestDue)
                .latestAnnouncements(
                        announcements.stream()
                                .map(a -> TenantDashboardResponse.AnnouncementItem.builder()
                                        .id(a.getId())
                                        .title(a.getTitle())
                                        .content(a.getContent())
                                        .createdAt(a.getCreatedAt())
                                        .build())
                                .toList()
                )
                .build();
    }

    @Override
    public List<TenantInvoiceSummaryResponse> getMyInvoices(String tenantId) {
        return getInvoicesByTenant(tenantId).stream()
                .map(this::toInvoiceSummary)
                .toList();
    }

    @Override
    public TenantInvoiceDetailResponse getInvoiceDetail(String tenantId, Long invoiceId) {
        List<Invoice> invoices = getInvoicesByTenant(tenantId);
        Invoice invoice = invoices.stream()
                .filter(i -> i.getId().equals(invoiceId))
                .findFirst()
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND.value(), "Invoice not found"));

        Contract contract = contractRepository.findById(Long.valueOf(invoice.getContractId()))
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND.value(), "Contract not found"));
        String roomId = contract.getRoomId();
        int month = invoice.getDueDate() != null ? invoice.getDueDate().getMonthValue() : LocalDate.now().getMonthValue();
        int year = invoice.getDueDate() != null ? invoice.getDueDate().getYear() : LocalDate.now().getYear();

        Map<Long, com.company.base.entity.Service> serviceMap = serviceRepository.findAll().stream()
                .collect(Collectors.toMap(com.company.base.entity.Service::getId, s -> s, (a, b) -> a));
        List<TenantInvoiceDetailResponse.ServiceItem> items = serviceUsageRepository
                .findByRoomIdAndMonthAndYearOrderByServiceIdAsc(roomId, month, year)
                .stream()
                .map(usage -> toServiceItem(usage, serviceMap))
                .toList();

        List<TenantInvoiceDetailResponse.PaymentItem> payments = paymentReceiptRepository
                .findByInvoiceIdOrderByPaymentTimeDescIdDesc(invoiceId)
                .stream()
                .map(receipt -> TenantInvoiceDetailResponse.PaymentItem.builder()
                        .receiptId(receipt.getId())
                        .receiptCode(receipt.getReceiptCode())
                        .amount(receipt.getAmount())
                        .paymentMethod(receipt.getPaymentMethod())
                        .paymentTime(receipt.getPaymentTime())
                        .build())
                .toList();

        return TenantInvoiceDetailResponse.builder()
                .id(invoice.getId())
                .invoiceCode(invoice.getInvoiceCode())
                .contractId(invoice.getContractId())
                .totalAmount(invoice.getTotalAmount())
                .status(invoice.getStatus())
                .dueDate(invoice.getDueDate())
                .paymentDate(invoice.getPaymentDate())
                .services(items)
                .paymentHistory(payments)
                .build();
    }

    @Override
    public List<TenantMaintenanceResponse> getMyMaintenanceRequests(String tenantId) {
        getTenant(tenantId);
        return maintenanceRequestRepository.findByTenantIdOrderByRequestedAtDescIdDesc(tenantId).stream()
                .map(this::toMaintenanceResponse)
                .toList();
    }

    @Override
    public TenantMaintenanceResponse createMaintenanceRequest(String tenantId, TenantMaintenanceRequest request) {
        Contract activeContract = getActiveContract(tenantId);
        if (activeContract == null) {
            throw new AppException(HttpStatus.BAD_REQUEST.value(), "Tenant has no active contract");
        }

        MaintenanceRequest mr = new MaintenanceRequest();
        mr.setTenantId(tenantId);
        mr.setRoomId(activeContract.getRoomId());
        mr.setTitle(request.getTitle());
        mr.setDescription(request.getDescription());
        mr.setPriority(request.getPriority());
        mr.setAttachmentFileId(request.getAttachmentFileId());
        mr.setStatus("RECEIVED");
        mr.setRequestedAt(java.time.LocalDateTime.now());
        return toMaintenanceResponse(maintenanceRequestRepository.save(mr));
    }

    @Override
    public TenantUtilityOverviewResponse getUtilities(String tenantId) {
        Tenant tenant = getTenant(tenantId);
        String roomId = getActiveContract(tenantId) != null ? getActiveContract(tenantId).getRoomId() : null;
        return buildUtilityOverview(tenant, roomId);
    }

    @Override
    public TenantUtilityOverviewResponse updateTemporaryResidence(String tenantId, TemporaryResidenceRequest request) {
        Tenant tenant = getTenant(tenantId);
        boolean declared = Boolean.TRUE.equals(request.getDeclared());
        tenant.setTemporaryResidenceDeclared(declared);
        tenant.setTemporaryResidenceDeclaredAt(declared
                ? (request.getDeclaredAt() != null ? request.getDeclaredAt() : LocalDate.now())
                : null);
        tenantRepository.save(tenant);
        String roomId = getActiveContract(tenantId) != null ? getActiveContract(tenantId).getRoomId() : null;
        return buildUtilityOverview(tenant, roomId);
    }

    @Override
    public TenantUtilityOverviewResponse registerVehicle(String tenantId, VehicleRegistrationRequest request) {
        Tenant tenant = getTenant(tenantId);
        Contract activeContract = getActiveContract(tenantId);
        String roomId = activeContract != null ? activeContract.getRoomId() : null;

        TenantVehicleRegistration vehicle = new TenantVehicleRegistration();
        vehicle.setTenantId(tenantId);
        vehicle.setRoomId(roomId);
        vehicle.setVehicleType(normalize(request.getVehicleType()));
        vehicle.setPlateNumber(request.getPlateNumber());
        vehicle.setRegisteredDate(LocalDate.now());
        vehicle.setStatus("PENDING");
        vehicle.setNote(request.getNote());
        vehicleRegistrationRepository.save(vehicle);

        return buildUtilityOverview(tenant, roomId);
    }

    private TenantUtilityOverviewResponse buildUtilityOverview(Tenant tenant, String roomId) {
        List<OperationsDocument> docs = operationsDocumentRepository
                .findByDocumentTypeIgnoreCaseAndActiveTrueOrderByTitleAsc("RULE");
        List<TenantVehicleRegistration> vehicles = vehicleRegistrationRepository
                .findByTenantIdOrderByRegisteredDateDescIdDesc(String.valueOf(tenant.getId()));

        return TenantUtilityOverviewResponse.builder()
                .temporaryResidenceDeclared(Boolean.TRUE.equals(tenant.getTemporaryResidenceDeclared()))
                .temporaryResidenceDeclaredAt(tenant.getTemporaryResidenceDeclaredAt())
                .rules(docs.stream()
                        .map(d -> TenantUtilityOverviewResponse.RuleDocumentItem.builder()
                                .id(d.getId())
                                .title(d.getTitle())
                                .fileId(d.getFileId())
                                .fileUrl(d.getFileId() != null ? "/api/v1/host/files/" + d.getFileId() : null)
                                .build())
                        .toList())
                .vehicles(vehicles.stream()
                        .map(v -> TenantUtilityOverviewResponse.VehicleItem.builder()
                                .id(v.getId())
                                .vehicleType(v.getVehicleType())
                                .plateNumber(v.getPlateNumber())
                                .status(v.getStatus())
                                .registeredDate(v.getRegisteredDate())
                                .note(v.getNote())
                                .build())
                        .toList())
                .build();
    }

    private List<Invoice> getInvoicesByTenant(String tenantId) {
        getTenant(tenantId);
        List<String> contractIds = contractRepository.findByTenantIdOrderByStartDateDescIdDesc(tenantId)
                .stream()
                .map(c -> String.valueOf(c.getId()))
                .toList();
        if (contractIds.isEmpty()) {
            return List.of();
        }
        return invoiceRepository.findByContractIdInOrderByDueDateDescIdDesc(contractIds);
    }

    private Tenant getTenant(String tenantId) {
        Long id;
        try {
            id = Long.valueOf(tenantId);
        } catch (Exception e) {
            throw new AppException(HttpStatus.BAD_REQUEST.value(), "Invalid tenantId");
        }
        return tenantRepository.findById(id)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND.value(), "Tenant not found"));
    }

    private Contract getActiveContract(String tenantId) {
        return contractRepository.findFirstByTenantIdAndStatusIgnoreCaseOrderByStartDateDescIdDesc(tenantId, "ACTIVE")
                .orElse(null);
    }

    private TenantInvoiceSummaryResponse toInvoiceSummary(Invoice invoice) {
        return TenantInvoiceSummaryResponse.builder()
                .id(invoice.getId())
                .invoiceCode(invoice.getInvoiceCode())
                .totalAmount(invoice.getTotalAmount())
                .status(invoice.getStatus())
                .dueDate(invoice.getDueDate())
                .paymentDate(invoice.getPaymentDate())
                .build();
    }

    private TenantInvoiceDetailResponse.ServiceItem toServiceItem(
            ServiceUsage usage,
            Map<Long, com.company.base.entity.Service> serviceMap
    ) {
        Long serviceId = null;
        try {
            serviceId = Long.valueOf(usage.getServiceId());
        } catch (Exception ignored) {
        }
        com.company.base.entity.Service service = serviceId != null ? serviceMap.get(serviceId) : null;
        Double consumption = (usage.getOldValue() != null && usage.getNewValue() != null)
                ? usage.getNewValue() - usage.getOldValue() : null;
        BigDecimal estimatedAmount = null;
        if (service != null && service.getUnitPrice() != null && consumption != null) {
            estimatedAmount = service.getUnitPrice().multiply(BigDecimal.valueOf(consumption));
        }
        return TenantInvoiceDetailResponse.ServiceItem.builder()
                .serviceId(usage.getServiceId())
                .serviceName(service != null ? service.getName() : null)
                .unitType(service != null ? service.getUnitType() : null)
                .unitPrice(service != null ? service.getUnitPrice() : null)
                .oldValue(usage.getOldValue())
                .newValue(usage.getNewValue())
                .consumption(consumption)
                .estimatedAmount(estimatedAmount)
                .build();
    }

    private TenantMaintenanceResponse toMaintenanceResponse(MaintenanceRequest mr) {
        return TenantMaintenanceResponse.builder()
                .id(mr.getId())
                .title(mr.getTitle())
                .description(mr.getDescription())
                .priority(mr.getPriority())
                .status(mr.getStatus())
                .assignedTechnician(mr.getAssignedTechnician())
                .attachmentFileId(mr.getAttachmentFileId())
                .attachmentUrl(mr.getAttachmentFileId() != null ? "/api/v1/host/files/" + mr.getAttachmentFileId() : null)
                .requestedAt(mr.getRequestedAt())
                .completedAt(mr.getCompletedAt())
                .note(mr.getNote())
                .build();
    }

    private String normalize(String value) {
        return value == null ? null : value.trim().toUpperCase();
    }
}
