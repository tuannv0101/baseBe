package com.company.base.service.impl;

import com.company.base.common.pagination.PageResponse;
import com.company.base.common.pagination.PaginationUtils;
import com.company.base.dto.response.host.DashboardOverviewResponse;
import com.company.base.dto.response.host.SystemNotificationResponse;
import com.company.base.entity.Contract;
import com.company.base.entity.ExpenseRecord;
import com.company.base.entity.Invoice;
import com.company.base.entity.MaintenanceRequest;
import com.company.base.entity.PaymentReceipt;
import com.company.base.repository.host.ContractRepository;
import com.company.base.repository.host.ExpenseRecordRepository;
import com.company.base.repository.host.InvoiceRepository;
import com.company.base.repository.host.MaintenanceRequestRepository;
import com.company.base.repository.host.PaymentReceiptRepository;
import com.company.base.repository.host.RoomRepository;
import com.company.base.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

/**
 * Service implementation containing business logic for this module.
 */

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private static final Set<String> DEBT_STATUSES = Set.of("UNPAID", "OVERDUE");
    private static final Set<String> NEW_MAINTENANCE_STATUSES = Set.of("RECEIVED", "ASSIGNED");

    private final PaymentReceiptRepository paymentReceiptRepository;
    private final ExpenseRecordRepository expenseRecordRepository;
    private final RoomRepository roomRepository;
    private final ContractRepository contractRepository;
    private final InvoiceRepository invoiceRepository;
    private final MaintenanceRequestRepository maintenanceRequestRepository;

    @Override
    public DashboardOverviewResponse getOverview(Integer month, Integer year) {
        LocalDate today = LocalDate.now();
        YearMonth target = YearMonth.of(year != null ? year : today.getYear(), month != null ? month : today.getMonthValue());
        LocalDate fromDate = target.atDay(1);
        LocalDate toDate = target.atEndOfMonth();

        BigDecimal income = sumReceipts(fromDate, toDate);
        BigDecimal expense = sumExpenses(fromDate, toDate);
        BigDecimal net = income.subtract(expense);

        long totalRooms = roomRepository.count();
        long occupiedRooms = countOccupiedRoomsAt(today);
        long vacantRooms = Math.max(totalRooms - occupiedRooms, 0);
        double occupancyRate = totalRooms == 0 ? 0.0 : (occupiedRooms * 100.0) / totalRooms;

        List<DashboardOverviewResponse.CashFlowPoint> chart = buildCashFlowChart(target, 6);

        return DashboardOverviewResponse.builder()
                .month(target.getMonthValue())
                .year(target.getYear())
                .actualRevenue(income)
                .totalIncome(income)
                .totalExpense(expense)
                .netCashFlow(net)
                .totalRooms(totalRooms)
                .occupiedRooms(occupiedRooms)
                .vacantRooms(vacantRooms)
                .occupancyRate(occupancyRate)
                .cashFlowChart(chart)
                .build();
    }

    @Override
    public PageResponse<SystemNotificationResponse> getSystemNotifications(Integer limit, Pageable pageable) {
        Pageable effectivePageable = pageable;
        if (limit != null && limit > 0) {
            effectivePageable = PageRequest.of(pageable.getPageNumber(), Math.min(limit, 100), pageable.getSort());
        }

        LocalDate today = LocalDate.now();
        LocalDate next30Days = today.plusDays(30);
        List<SystemNotificationResponse> notifications = new ArrayList<>();

        List<Invoice> debtInvoices = invoiceRepository.findByStatusInAndDueDateBeforeOrderByDueDateAscIdDesc(DEBT_STATUSES, today);
        for (Invoice invoice : debtInvoices) {
            notifications.add(SystemNotificationResponse.builder()
                    .type("DEBT")
                    .severity("HIGH")
                    .title("Hoa don qua han")
                    .message("Hoa don " + safe(invoice.getInvoiceCode()) + " da qua han thanh toan")
                    .createdAt(invoice.getDueDate() != null ? invoice.getDueDate().atStartOfDay() : LocalDateTime.now())
                    .referenceType("INVOICE")
                    .referenceId(String.valueOf(invoice.getId()))
                    .build());
        }

        List<Contract> expiringContracts =
                contractRepository.findByEndDateBetweenAndStatusIgnoreCaseOrderByEndDateAscIdDesc(today, next30Days, "ACTIVE");
        for (Contract contract : expiringContracts) {
            notifications.add(SystemNotificationResponse.builder()
                    .type("CONTRACT_EXPIRING")
                    .severity("MEDIUM")
                    .title("Hop dong sap het han")
                    .message("Hop dong #" + contract.getId() + " se het han vao " + contract.getEndDate())
                    .createdAt(contract.getEndDate() != null ? contract.getEndDate().atStartOfDay() : LocalDateTime.now())
                    .referenceType("CONTRACT")
                    .referenceId(String.valueOf(contract.getId()))
                    .build());
        }

        List<MaintenanceRequest> newRequests =
                maintenanceRequestRepository.findTop20ByStatusInOrderByRequestedAtDescIdDesc(NEW_MAINTENANCE_STATUSES);
        for (MaintenanceRequest request : newRequests) {
            notifications.add(SystemNotificationResponse.builder()
                    .type("MAINTENANCE")
                    .severity("MEDIUM")
                    .title("Yeu cau sua chua moi")
                    .message("Yeu cau #" + request.getId() + " - " + safe(request.getTitle()))
                    .createdAt(request.getRequestedAt() != null ? request.getRequestedAt() : LocalDateTime.now())
                    .referenceType("MAINTENANCE_REQUEST")
                    .referenceId(String.valueOf(request.getId()))
                    .build());
        }

        List<SystemNotificationResponse> sorted = notifications.stream()
                .sorted(Comparator.comparing(SystemNotificationResponse::getCreatedAt, Comparator.nullsLast(Comparator.reverseOrder())))
                .toList();
        return PaginationUtils.paginateList(sorted, effectivePageable);
    }

    private List<DashboardOverviewResponse.CashFlowPoint> buildCashFlowChart(YearMonth target, int monthsBack) {
        List<DashboardOverviewResponse.CashFlowPoint> points = new ArrayList<>();
        YearMonth start = target.minusMonths(monthsBack - 1L);
        YearMonth current = start;
        while (!current.isAfter(target)) {
            LocalDate fromDate = current.atDay(1);
            LocalDate toDate = current.atEndOfMonth();
            BigDecimal income = sumReceipts(fromDate, toDate);
            BigDecimal expense = sumExpenses(fromDate, toDate);
            points.add(DashboardOverviewResponse.CashFlowPoint.builder()
                    .month(current.getMonthValue())
                    .year(current.getYear())
                    .income(income)
                    .expense(expense)
                    .net(income.subtract(expense))
                    .build());
            current = current.plusMonths(1);
        }
        return points;
    }

    private BigDecimal sumReceipts(LocalDate fromDate, LocalDate toDate) {
        LocalDateTime fromDateTime = fromDate.atStartOfDay();
        LocalDateTime toDateTime = toDate.atTime(23, 59, 59);
        return paymentReceiptRepository.findByPaymentTimeBetweenOrderByPaymentTimeAscIdAsc(fromDateTime, toDateTime)
                .stream()
                .map(PaymentReceipt::getAmount)
                .filter(amount -> amount != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal sumExpenses(LocalDate fromDate, LocalDate toDate) {
        return expenseRecordRepository.findByExpenseDateBetweenOrderByExpenseDateDescIdDesc(fromDate, toDate)
                .stream()
                .map(ExpenseRecord::getAmount)
                .filter(amount -> amount != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private long countOccupiedRoomsAt(LocalDate date) {
        return contractRepository.findByStatusIgnoreCaseOrderByStartDateDescIdDesc("ACTIVE")
                .stream()
                .filter(contract -> overlaps(contract, date))
                .map(Contract::getRoomId)
                .filter(roomId -> roomId != null && !roomId.isBlank())
                .distinct()
                .count();
    }

    private boolean overlaps(Contract contract, LocalDate date) {
        LocalDate start = contract.getStartDate();
        LocalDate end = contract.getEndDate();
        boolean startOk = start == null || !start.isAfter(date);
        boolean endOk = end == null || !end.isBefore(date);
        return startOk && endOk;
    }

    private String safe(String value) {
        return value == null || value.isBlank() ? "N/A" : value;
    }
}
