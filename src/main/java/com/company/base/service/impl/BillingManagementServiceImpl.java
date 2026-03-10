package com.company.base.service.impl;

import com.company.base.common.pagination.PageResponse;
import com.company.base.common.pagination.PaginationUtils;
import com.company.base.dto.request.host.BillingServiceRequest;
import com.company.base.dto.request.host.InvoiceRequest;
import com.company.base.dto.request.host.MeterReadingBulkRequest;
import com.company.base.dto.request.host.PaymentReceiptRequest;
import com.company.base.dto.response.host.BillingServiceResponse;
import com.company.base.dto.response.host.InvoiceResponse;
import com.company.base.dto.response.host.PaymentReceiptResponse;
import com.company.base.dto.response.host.ServiceUsageResponse;
import com.company.base.entity.Invoice;
import com.company.base.entity.PaymentReceipt;
import com.company.base.entity.ServiceUsage;
import com.company.base.exception.AppException;
import com.company.base.repository.host.InvoiceRepository;
import com.company.base.repository.host.PaymentReceiptRepository;
import com.company.base.repository.host.ServiceRepository;
import com.company.base.repository.host.ServiceUsageRepository;
import com.company.base.service.BillingManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Set;

/**
 * Service implementation containing business logic for this module.
 */

@Service
@RequiredArgsConstructor
public class BillingManagementServiceImpl implements BillingManagementService {

    private static final Set<String> OVERDUE_STATUSES = Set.of("UNPAID", "OVERDUE");

    private final ServiceRepository serviceRepository;
    private final ServiceUsageRepository serviceUsageRepository;
    private final InvoiceRepository invoiceRepository;
    private final PaymentReceiptRepository paymentReceiptRepository;

    @Override
    public BillingServiceResponse createService(BillingServiceRequest request) {
        com.company.base.entity.Service entity = new com.company.base.entity.Service();
        applyServiceUpdate(entity, request);
        return toServiceResponse(serviceRepository.save(entity));
    }

    @Override
    public BillingServiceResponse updateService(Long id, BillingServiceRequest request) {
        com.company.base.entity.Service entity = getServiceEntity(id);
        applyServiceUpdate(entity, request);
        return toServiceResponse(serviceRepository.save(entity));
    }

    @Override
    public BillingServiceResponse getServiceById(Long id) {
        return toServiceResponse(getServiceEntity(id));
    }

    @Override
    public PageResponse<BillingServiceResponse> getAllServices(Pageable pageable) {
        Page<BillingServiceResponse> page = serviceRepository.findAllByOrderByNameAsc(pageable)
                .map(this::toServiceResponse);
        return PageResponse.of(page);
    }

    @Override
    public void deleteService(Long id) {
        com.company.base.entity.Service entity = getServiceEntity(id);
        entity.setDelYn("Y");
        serviceRepository.save(entity);
    }

    @Override
    public List<ServiceUsageResponse> saveMeterReadingsBulk(MeterReadingBulkRequest request) {
        if (request.getReadings() == null || request.getReadings().isEmpty()) {
            return List.of();
        }

        return request.getReadings().stream().map(row -> {
            ServiceUsage usage = serviceUsageRepository
                    .findByRoomIdAndServiceIdAndMonthAndYear(
                            row.getRoomId(),
                            request.getServiceId(),
                            request.getMonth(),
                            request.getYear()
                    )
                    .orElseGet(ServiceUsage::new);

            usage.setRoomId(row.getRoomId());
            usage.setServiceId(request.getServiceId());
            usage.setMonth(request.getMonth());
            usage.setYear(request.getYear());
            usage.setOldValue(row.getOldValue());
            usage.setNewValue(row.getNewValue());
            return toUsageResponse(serviceUsageRepository.save(usage));
        }).toList();
    }

    @Override
    public PageResponse<ServiceUsageResponse> getServiceUsage(Integer month, Integer year, String serviceId, Pageable pageable) {
        Page<ServiceUsageResponse> page;
        if (serviceId == null || serviceId.isBlank()) {
            page = serviceUsageRepository.findByMonthAndYearOrderByRoomIdAsc(month, year, pageable)
                    .map(this::toUsageResponse);
        } else {
            page = serviceUsageRepository.findByServiceIdAndMonthAndYearOrderByRoomIdAsc(serviceId, month, year, pageable)
                    .map(this::toUsageResponse);
        }
        return PageResponse.of(page);
    }

    @Override
    public InvoiceResponse createInvoice(InvoiceRequest request) {
        Invoice entity = new Invoice();
        applyInvoiceUpdate(entity, request);
        return toInvoiceResponse(invoiceRepository.save(entity));
    }

    @Override
    public InvoiceResponse updateInvoice(Long id, InvoiceRequest request) {
        Invoice entity = getInvoiceEntity(id);
        applyInvoiceUpdate(entity, request);
        return toInvoiceResponse(invoiceRepository.save(entity));
    }

    @Override
    public InvoiceResponse getInvoiceById(Long id) {
        return toInvoiceResponse(getInvoiceEntity(id));
    }

    @Override
    public PageResponse<InvoiceResponse> getCurrentMonthInvoices(Integer month, Integer year, Pageable pageable) {
        LocalDate now = LocalDate.now();
        int targetMonth = month != null ? month : now.getMonthValue();
        int targetYear = year != null ? year : now.getYear();
        YearMonth ym = YearMonth.of(targetYear, targetMonth);
        LocalDate fromDate = ym.atDay(1);
        LocalDate toDate = ym.atEndOfMonth();

        Page<InvoiceResponse> page = invoiceRepository.findByDueDateBetweenOrderByDueDateAscIdDesc(fromDate, toDate, pageable)
                .map(this::toInvoiceResponse);
        return PageResponse.of(page);
    }

    @Override
    public PageResponse<InvoiceResponse> getOverdueInvoices(Pageable pageable) {
        LocalDate today = LocalDate.now();
        // Preserve existing behavior (auto-mark UNPAID -> OVERDUE) then paginate results.
        List<InvoiceResponse> all = invoiceRepository.findByStatusInAndDueDateBeforeOrderByDueDateAscIdDesc(OVERDUE_STATUSES, today)
                .stream()
                .map(invoice -> {
                    if ("UNPAID".equalsIgnoreCase(invoice.getStatus())) {
                        invoice.setStatus("OVERDUE");
                        invoiceRepository.save(invoice);
                    }
                    return toInvoiceResponse(invoice);
                })
                .toList();
        return PaginationUtils.paginateList(all, pageable);
    }

    @Override
    public PaymentReceiptResponse createPaymentReceipt(PaymentReceiptRequest request) {
        Invoice invoice = getInvoiceEntity(request.getInvoiceId());
        invoice.setStatus("PAID");
        invoice.setPaymentDate(request.getPaymentTime() != null ? request.getPaymentTime() : LocalDateTime.now());
        invoiceRepository.save(invoice);

        PaymentReceipt receipt = new PaymentReceipt();
        receipt.setInvoiceId(request.getInvoiceId());
        receipt.setReceiptCode(request.getReceiptCode());
        receipt.setRoomId(request.getRoomId());
        receipt.setPayerName(request.getPayerName());
        receipt.setAmount(request.getAmount());
        receipt.setPaymentMethod(request.getPaymentMethod());
        receipt.setPaymentTime(request.getPaymentTime() != null ? request.getPaymentTime() : LocalDateTime.now());
        receipt.setNote(request.getNote());
        return toPaymentReceiptResponse(paymentReceiptRepository.save(receipt));
    }

    @Override
    public PageResponse<PaymentReceiptResponse> getPaymentHistory(Pageable pageable) {
        Page<PaymentReceiptResponse> page = paymentReceiptRepository.findAllByOrderByPaymentTimeDescIdDesc(pageable)
                .map(this::toPaymentReceiptResponse);
        return PageResponse.of(page);
    }

    private void applyServiceUpdate(com.company.base.entity.Service entity, BillingServiceRequest request) {
        entity.setName(request.getName());
        entity.setUnitPrice(request.getUnitPrice());
        entity.setUnitType(request.getUnitType());
    }

    private void applyInvoiceUpdate(Invoice entity, InvoiceRequest request) {
        entity.setContractId(request.getContractId());
        entity.setInvoiceCode(request.getInvoiceCode());
        entity.setTotalAmount(request.getTotalAmount());
        entity.setStatus(normalizeStatus(request.getStatus()));
        entity.setDueDate(request.getDueDate());
    }

    private String normalizeStatus(String status) {
        return status == null ? null : status.trim().toUpperCase();
    }

    private com.company.base.entity.Service getServiceEntity(Long id) {
        return serviceRepository.findById(id)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND.value(), "Service not found"));
    }

    private Invoice getInvoiceEntity(Long id) {
        return invoiceRepository.findById(id)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND.value(), "Invoice not found"));
    }

    private BillingServiceResponse toServiceResponse(com.company.base.entity.Service entity) {
        return BillingServiceResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .unitPrice(entity.getUnitPrice())
                .unitType(entity.getUnitType())
                .build();
    }

    private ServiceUsageResponse toUsageResponse(ServiceUsage entity) {
        Double oldValue = entity.getOldValue();
        Double newValue = entity.getNewValue();
        Double consumption = (oldValue != null && newValue != null) ? newValue - oldValue : null;
        return ServiceUsageResponse.builder()
                .id(entity.getId())
                .roomId(entity.getRoomId())
                .serviceId(entity.getServiceId())
                .month(entity.getMonth())
                .year(entity.getYear())
                .oldValue(oldValue)
                .newValue(newValue)
                .consumption(consumption)
                .build();
    }

    private InvoiceResponse toInvoiceResponse(Invoice entity) {
        return InvoiceResponse.builder()
                .id(entity.getId())
                .contractId(entity.getContractId())
                .invoiceCode(entity.getInvoiceCode())
                .totalAmount(entity.getTotalAmount())
                .status(entity.getStatus())
                .dueDate(entity.getDueDate())
                .paymentDate(entity.getPaymentDate())
                .build();
    }

    private PaymentReceiptResponse toPaymentReceiptResponse(PaymentReceipt entity) {
        return PaymentReceiptResponse.builder()
                .id(entity.getId())
                .invoiceId(entity.getInvoiceId())
                .receiptCode(entity.getReceiptCode())
                .roomId(entity.getRoomId())
                .payerName(entity.getPayerName())
                .amount(entity.getAmount())
                .paymentMethod(entity.getPaymentMethod())
                .paymentTime(entity.getPaymentTime())
                .note(entity.getNote())
                .build();
    }
}
