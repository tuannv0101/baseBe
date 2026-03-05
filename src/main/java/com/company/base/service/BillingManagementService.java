package com.company.base.service;

import com.company.base.dto.request.BillingServiceRequest;
import com.company.base.dto.request.InvoiceRequest;
import com.company.base.dto.request.MeterReadingBulkRequest;
import com.company.base.dto.request.PaymentReceiptRequest;
import com.company.base.dto.response.BillingServiceResponse;
import com.company.base.dto.response.InvoiceResponse;
import com.company.base.dto.response.PaymentReceiptResponse;
import com.company.base.dto.response.ServiceUsageResponse;

import java.util.List;
/**
 * Service contract defining operations for this module.
 */

public interface BillingManagementService {
    BillingServiceResponse createService(BillingServiceRequest request);
    BillingServiceResponse updateService(Long id, BillingServiceRequest request);
    BillingServiceResponse getServiceById(Long id);
    List<BillingServiceResponse> getAllServices();
    void deleteService(Long id);

    List<ServiceUsageResponse> saveMeterReadingsBulk(MeterReadingBulkRequest request);
    List<ServiceUsageResponse> getServiceUsage(Integer month, Integer year, String serviceId);

    InvoiceResponse createInvoice(InvoiceRequest request);
    InvoiceResponse updateInvoice(Long id, InvoiceRequest request);
    InvoiceResponse getInvoiceById(Long id);
    List<InvoiceResponse> getCurrentMonthInvoices(Integer month, Integer year);
    List<InvoiceResponse> getOverdueInvoices();

    PaymentReceiptResponse createPaymentReceipt(PaymentReceiptRequest request);
    List<PaymentReceiptResponse> getPaymentHistory();
}
