package com.company.base.service;

import com.company.base.dto.request.host.BillingServiceRequest;
import com.company.base.dto.request.host.InvoiceRequest;
import com.company.base.dto.request.host.MeterReadingBulkRequest;
import com.company.base.dto.request.host.PaymentReceiptRequest;
import com.company.base.dto.response.host.BillingServiceResponse;
import com.company.base.dto.response.host.InvoiceResponse;
import com.company.base.dto.response.host.PaymentReceiptResponse;
import com.company.base.dto.response.host.ServiceUsageResponse;
import com.company.base.common.pagination.PageResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service quản lý nghiệp vụ thu tiền, hóa đơn và dịch vụ (điện/nước/khác).
 */
public interface BillingManagementService {
    /**
     * Tạo mới một dịch vụ tính phí (ví dụ: điện, nước, internet...).
     */
    BillingServiceResponse createService(BillingServiceRequest request);

    /**
     * Cập nhật thông tin dịch vụ theo ID.
     */
    BillingServiceResponse updateService(String id, BillingServiceRequest request);

    /**
     * Lấy chi tiết dịch vụ theo ID.
     */
    BillingServiceResponse getServiceById(String id);

    /**
     * Lấy danh sách toàn bộ dịch vụ đang khai báo.
     */
    PageResponse<BillingServiceResponse> getAllServices(String propertyId, Pageable pageable);

    /**
     * Xóa dịch vụ theo ID.
     */
    void deleteService(String id);

    /**
     * Lưu hàng loạt chỉ số/định mức sử dụng (bulk meter readings) cho nhiều phòng/dịch vụ.
     */
    List<ServiceUsageResponse> saveMeterReadingsBulk(MeterReadingBulkRequest request);

    /**
     * Lấy danh sách sử dụng dịch vụ theo tháng/năm (có thể lọc theo serviceId).
     */
    PageResponse<ServiceUsageResponse> getServiceUsage(Integer month, Integer year, String serviceId, Pageable pageable);

    /**
     * Tạo hóa đơn theo yêu cầu nghiệp vụ.
     */
    InvoiceResponse createInvoice(InvoiceRequest request);

    /**
     * Cập nhật hóa đơn theo ID.
     */
    InvoiceResponse updateInvoice(String id, InvoiceRequest request);

    /**
     * Lấy chi tiết hóa đơn theo ID.
     */
    InvoiceResponse getInvoiceById(String id);

    /**
     * Lấy danh sách hóa đơn của một tháng/năm chỉ định (thường là tháng hiện tại).
     */
    PageResponse<InvoiceResponse> getCurrentMonthInvoices(Integer month, Integer year, Pageable pageable);

    /**
     * Lấy danh sách hóa đơn quá hạn thanh toán.
     */
    PageResponse<InvoiceResponse> getOverdueInvoices(Pageable pageable);

    /**
     * Tạo phiếu thu/ghi nhận thanh toán cho hóa đơn.
     */
    PaymentReceiptResponse createPaymentReceipt(PaymentReceiptRequest request);

    /**
     * Lấy lịch sử các lần thanh toán/phiếu thu.
     */
    PageResponse<PaymentReceiptResponse> getPaymentHistory(Pageable pageable);
}
