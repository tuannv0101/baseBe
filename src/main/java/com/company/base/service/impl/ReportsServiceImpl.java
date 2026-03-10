package com.company.base.service.impl;

import com.company.base.dto.response.host.DebtReportResponse;
import com.company.base.dto.response.host.RevenueReportResponse;
import com.company.base.dto.response.host.VacancyRateReportResponse;
import com.company.base.entity.Contract;
import com.company.base.entity.Invoice;
import com.company.base.entity.PaymentReceipt;
import com.company.base.exception.AppException;
import com.company.base.repository.host.ContractRepository;
import com.company.base.repository.host.InvoiceRepository;
import com.company.base.repository.host.PaymentReceiptRepository;
import com.company.base.repository.host.RoomManagementRepository;
import com.company.base.service.ReportsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service implementation containing business logic for this module.
 */

@Service
@RequiredArgsConstructor
public class ReportsServiceImpl implements ReportsService {

    private static final Set<String> DEBT_STATUSES = Set.of("UNPAID", "OVERDUE");

    private final InvoiceRepository invoiceRepository;
    private final PaymentReceiptRepository paymentReceiptRepository;
    private final ContractRepository contractRepository;
    private final RoomManagementRepository roomManagementRepository;

    @Override
    public RevenueReportResponse getRevenueReport(Integer fromMonth, Integer fromYear, Integer toMonth, Integer toYear) {
        MonthRange range = resolveRange(fromMonth, fromYear, toMonth, toYear);
        List<RevenueReportResponse.RevenuePoint> points = new ArrayList<>();
        BigDecimal totalExpected = BigDecimal.ZERO;
        BigDecimal totalActual = BigDecimal.ZERO;

        for (YearMonth ym : range.months) {
            LocalDate fromDate = ym.atDay(1);
            LocalDate toDate = ym.atEndOfMonth();
            LocalDateTime fromDateTime = fromDate.atStartOfDay();
            LocalDateTime toDateTime = toDate.atTime(23, 59, 59);

            BigDecimal expected = sumInvoiceAmount(
                    invoiceRepository.findByDueDateBetweenOrderByDueDateAscIdDesc(fromDate, toDate)
            );
            BigDecimal actual = sumReceiptAmount(
                    paymentReceiptRepository.findByPaymentTimeBetweenOrderByPaymentTimeAscIdAsc(fromDateTime, toDateTime)
            );

            totalExpected = totalExpected.add(expected);
            totalActual = totalActual.add(actual);

            points.add(RevenueReportResponse.RevenuePoint.builder()
                    .month(ym.getMonthValue())
                    .year(ym.getYear())
                    .expected(expected)
                    .actual(actual)
                    .variance(actual.subtract(expected))
                    .build());
        }

        return RevenueReportResponse.builder()
                .fromMonth(range.start.getMonthValue())
                .fromYear(range.start.getYear())
                .toMonth(range.end.getMonthValue())
                .toYear(range.end.getYear())
                .totalExpected(totalExpected)
                .totalActual(totalActual)
                .variance(totalActual.subtract(totalExpected))
                .points(points)
                .build();
    }

    @Override
    public VacancyRateReportResponse getVacancyRateReport(Integer fromMonth, Integer fromYear, Integer toMonth, Integer toYear) {
        MonthRange range = resolveRange(fromMonth, fromYear, toMonth, toYear);
        long totalRooms = roomManagementRepository.count();
        List<Contract> activeContracts = contractRepository.findByStatusIgnoreCaseOrderByStartDateDescIdDesc("ACTIVE");
        List<VacancyRateReportResponse.VacancyPoint> points = new ArrayList<>();

        for (YearMonth ym : range.months) {
            LocalDate monthStart = ym.atDay(1);
            LocalDate monthEnd = ym.atEndOfMonth();

            long occupiedRooms = activeContracts.stream()
                    .filter(contract -> overlaps(contract, monthStart, monthEnd))
                    .map(Contract::getRoomId)
                    .filter(roomId -> roomId != null && !roomId.isBlank())
                    .distinct()
                    .count();

            if (occupiedRooms > totalRooms) {
                occupiedRooms = totalRooms;
            }

            long vacantRooms = Math.max(totalRooms - occupiedRooms, 0);
            double vacancyRate = totalRooms == 0 ? 0.0 : (vacantRooms * 100.0) / totalRooms;

            points.add(VacancyRateReportResponse.VacancyPoint.builder()
                    .month(ym.getMonthValue())
                    .year(ym.getYear())
                    .totalRooms(totalRooms)
                    .occupiedRooms(occupiedRooms)
                    .vacantRooms(vacantRooms)
                    .vacancyRate(vacancyRate)
                    .build());
        }

        double avgVacancy = points.stream()
                .map(VacancyRateReportResponse.VacancyPoint::getVacancyRate)
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);

        return VacancyRateReportResponse.builder()
                .fromMonth(range.start.getMonthValue())
                .fromYear(range.start.getYear())
                .toMonth(range.end.getMonthValue())
                .toYear(range.end.getYear())
                .totalRooms(totalRooms)
                .averageVacancyRate(avgVacancy)
                .points(points)
                .build();
    }

    @Override
    public DebtReportResponse getDebtReport() {
        LocalDate today = LocalDate.now();
        List<Invoice> debtInvoices = invoiceRepository.findByStatusInOrderByDueDateAscIdDesc(DEBT_STATUSES);
        Map<String, Contract> contractById = contractRepository.findAll().stream()
                .collect(Collectors.toMap(contract -> String.valueOf(contract.getId()), contract -> contract, (a, b) -> a));

        List<DebtReportResponse.DebtItem> items = debtInvoices.stream()
                .sorted(Comparator.comparing(Invoice::getDueDate, Comparator.nullsLast(Comparator.naturalOrder())))
                .map(invoice -> toDebtItem(invoice, contractById.get(invoice.getContractId()), today))
                .toList();

        BigDecimal totalOutstanding = items.stream()
                .map(DebtReportResponse.DebtItem::getAmount)
                .filter(amount -> amount != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalOverdue = items.stream()
                .filter(item -> item.getDaysPastDue() != null && item.getDaysPastDue() > 0)
                .map(DebtReportResponse.DebtItem::getAmount)
                .filter(amount -> amount != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return DebtReportResponse.builder()
                .reportDate(today)
                .totalOutstanding(totalOutstanding)
                .totalOverdue(totalOverdue)
                .items(items)
                .build();
    }

    private boolean overlaps(Contract contract, LocalDate start, LocalDate end) {
        LocalDate contractStart = contract.getStartDate();
        LocalDate contractEnd = contract.getEndDate();
        boolean startOk = contractStart == null || !contractStart.isAfter(end);
        boolean endOk = contractEnd == null || !contractEnd.isBefore(start);
        return startOk && endOk;
    }

    private DebtReportResponse.DebtItem toDebtItem(Invoice invoice, Contract contract, LocalDate today) {
        LocalDate dueDate = invoice.getDueDate();
        Long daysPastDue = null;
        if (dueDate != null && dueDate.isBefore(today)) {
            daysPastDue = ChronoUnit.DAYS.between(dueDate, today);
        }

        return DebtReportResponse.DebtItem.builder()
                .invoiceId(invoice.getId())
                .invoiceCode(invoice.getInvoiceCode())
                .contractId(invoice.getContractId())
                .tenantId(contract != null ? contract.getTenantId() : null)
                .roomId(contract != null ? contract.getRoomId() : null)
                .amount(invoice.getTotalAmount())
                .status(invoice.getStatus())
                .dueDate(dueDate)
                .daysPastDue(daysPastDue)
                .build();
    }

    private BigDecimal sumInvoiceAmount(List<Invoice> invoices) {
        return invoices.stream()
                .map(Invoice::getTotalAmount)
                .filter(amount -> amount != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal sumReceiptAmount(List<PaymentReceipt> receipts) {
        return receipts.stream()
                .map(PaymentReceipt::getAmount)
                .filter(amount -> amount != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private MonthRange resolveRange(Integer fromMonth, Integer fromYear, Integer toMonth, Integer toYear) {
        LocalDate today = LocalDate.now();
        YearMonth start = buildYearMonth(
                fromMonth != null ? fromMonth : today.getMonthValue(),
                fromYear != null ? fromYear : today.getYear()
        );
        YearMonth end = buildYearMonth(
                toMonth != null ? toMonth : start.getMonthValue(),
                toYear != null ? toYear : start.getYear()
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

    private YearMonth buildYearMonth(int month, int year) {
        if (month < 1 || month > 12) {
            throw new AppException(HttpStatus.BAD_REQUEST.value(), "Month must be between 1 and 12");
        }
        return YearMonth.of(year, month);
    }

    private record MonthRange(YearMonth start, YearMonth end, List<YearMonth> months) {
    }
}
