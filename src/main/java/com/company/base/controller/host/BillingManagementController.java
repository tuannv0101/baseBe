package com.company.base.controller.host;

import com.company.base.common.ApiResponse;
import com.company.base.dto.request.host.BillingServiceRequest;
import com.company.base.dto.request.host.InvoiceRequest;
import com.company.base.dto.request.host.MeterReadingBulkRequest;
import com.company.base.dto.request.host.PaymentReceiptRequest;
import com.company.base.dto.response.host.BillingServiceResponse;
import com.company.base.dto.response.host.InvoiceResponse;
import com.company.base.dto.response.host.PaymentReceiptResponse;
import com.company.base.dto.response.host.ServiceUsageResponse;
import com.company.base.service.BillingManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.company.base.common.pagination.PageResponse;

/**
 * REST controller that exposes API endpoints for this module.
 */

@RestController
@RequestMapping("/api/v1/host/billing")
@RequiredArgsConstructor
public class BillingManagementController {

    private final BillingManagementService billingManagementService;

    @PostMapping("/services")
    public ApiResponse<BillingServiceResponse> createService(@RequestBody BillingServiceRequest request) {
        return ApiResponse.success(billingManagementService.createService(request));
    }

    @PutMapping("/services/{id}")
    public ApiResponse<BillingServiceResponse> updateService(@PathVariable Long id, @RequestBody BillingServiceRequest request) {
        return ApiResponse.success(billingManagementService.updateService(id, request));
    }

    @GetMapping("/services/{id}")
    public ApiResponse<BillingServiceResponse> getServiceById(@PathVariable Long id) {
        return ApiResponse.success(billingManagementService.getServiceById(id));
    }

    @GetMapping("/services")
    public ApiResponse<PageResponse<BillingServiceResponse>> getAllServices(@PageableDefault(size = 20) Pageable pageable) {
        return ApiResponse.success(billingManagementService.getAllServices(pageable));
    }

    @PutMapping("/services/{id}/delete")
    public ApiResponse<Void> deleteService(@PathVariable Long id) {
        billingManagementService.deleteService(id);
        return ApiResponse.success(null);
    }

    @PostMapping("/meter-readings/bulk")
    public ApiResponse<List<ServiceUsageResponse>> saveMeterReadingsBulk(@RequestBody MeterReadingBulkRequest request) {
        return ApiResponse.success(billingManagementService.saveMeterReadingsBulk(request));
    }

    @GetMapping("/meter-readings")
    public ApiResponse<PageResponse<ServiceUsageResponse>> getServiceUsage(
            @RequestParam Integer month,
            @RequestParam Integer year,
            @RequestParam(required = false) String serviceId,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        return ApiResponse.success(billingManagementService.getServiceUsage(month, year, serviceId, pageable));
    }

    @PostMapping("/invoices")
    public ApiResponse<InvoiceResponse> createInvoice(@RequestBody InvoiceRequest request) {
        return ApiResponse.success(billingManagementService.createInvoice(request));
    }

    @PutMapping("/invoices/{id}")
    public ApiResponse<InvoiceResponse> updateInvoice(@PathVariable Long id, @RequestBody InvoiceRequest request) {
        return ApiResponse.success(billingManagementService.updateInvoice(id, request));
    }

    @GetMapping("/invoices/{id}")
    public ApiResponse<InvoiceResponse> getInvoiceById(@PathVariable Long id) {
        return ApiResponse.success(billingManagementService.getInvoiceById(id));
    }

    @GetMapping("/invoices/current-month")
    public ApiResponse<PageResponse<InvoiceResponse>> getCurrentMonthInvoices(
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer year,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        return ApiResponse.success(billingManagementService.getCurrentMonthInvoices(month, year, pageable));
    }

    @GetMapping("/invoices/overdue")
    public ApiResponse<PageResponse<InvoiceResponse>> getOverdueInvoices(@PageableDefault(size = 20) Pageable pageable) {
        return ApiResponse.success(billingManagementService.getOverdueInvoices(pageable));
    }

    @PostMapping("/receipts")
    public ApiResponse<PaymentReceiptResponse> createPaymentReceipt(@RequestBody PaymentReceiptRequest request) {
        return ApiResponse.success(billingManagementService.createPaymentReceipt(request));
    }

    @GetMapping("/receipts/history")
    public ApiResponse<PageResponse<PaymentReceiptResponse>> getPaymentHistory(@PageableDefault(size = 20) Pageable pageable) {
        return ApiResponse.success(billingManagementService.getPaymentHistory(pageable));
    }
}
