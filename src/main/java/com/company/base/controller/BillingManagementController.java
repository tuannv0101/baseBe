package com.company.base.controller;

import com.company.base.common.ApiResponse;
import com.company.base.dto.request.BillingServiceRequest;
import com.company.base.dto.request.InvoiceRequest;
import com.company.base.dto.request.MeterReadingBulkRequest;
import com.company.base.dto.request.PaymentReceiptRequest;
import com.company.base.dto.response.BillingServiceResponse;
import com.company.base.dto.response.InvoiceResponse;
import com.company.base.dto.response.PaymentReceiptResponse;
import com.company.base.dto.response.ServiceUsageResponse;
import com.company.base.service.BillingManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
/**
 * REST controller that exposes API endpoints for this module.
 */

@RestController
@RequestMapping("/api/v1/billing")
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
    public ApiResponse<List<BillingServiceResponse>> getAllServices() {
        return ApiResponse.success(billingManagementService.getAllServices());
    }

    @DeleteMapping("/services/{id}")
    public ApiResponse<Void> deleteService(@PathVariable Long id) {
        billingManagementService.deleteService(id);
        return ApiResponse.success(null);
    }

    @PostMapping("/meter-readings/bulk")
    public ApiResponse<List<ServiceUsageResponse>> saveMeterReadingsBulk(@RequestBody MeterReadingBulkRequest request) {
        return ApiResponse.success(billingManagementService.saveMeterReadingsBulk(request));
    }

    @GetMapping("/meter-readings")
    public ApiResponse<List<ServiceUsageResponse>> getServiceUsage(
            @RequestParam Integer month,
            @RequestParam Integer year,
            @RequestParam(required = false) String serviceId
    ) {
        return ApiResponse.success(billingManagementService.getServiceUsage(month, year, serviceId));
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
    public ApiResponse<List<InvoiceResponse>> getCurrentMonthInvoices(
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer year
    ) {
        return ApiResponse.success(billingManagementService.getCurrentMonthInvoices(month, year));
    }

    @GetMapping("/invoices/overdue")
    public ApiResponse<List<InvoiceResponse>> getOverdueInvoices() {
        return ApiResponse.success(billingManagementService.getOverdueInvoices());
    }

    @PostMapping("/receipts")
    public ApiResponse<PaymentReceiptResponse> createPaymentReceipt(@RequestBody PaymentReceiptRequest request) {
        return ApiResponse.success(billingManagementService.createPaymentReceipt(request));
    }

    @GetMapping("/receipts/history")
    public ApiResponse<List<PaymentReceiptResponse>> getPaymentHistory() {
        return ApiResponse.success(billingManagementService.getPaymentHistory());
    }
}
