package com.company.base.service;

import com.company.base.dto.request.tenant.TemporaryResidenceRequest;
import com.company.base.dto.request.tenant.TenantMaintenanceRequest;
import com.company.base.dto.request.tenant.VehicleRegistrationRequest;
import com.company.base.dto.response.tenant.TenantDashboardResponse;
import com.company.base.dto.response.tenant.TenantInvoiceDetailResponse;
import com.company.base.dto.response.tenant.TenantInvoiceSummaryResponse;
import com.company.base.dto.response.tenant.TenantMaintenanceResponse;
import com.company.base.dto.response.tenant.TenantUtilityOverviewResponse;

import com.company.base.common.pagination.PageResponse;
import org.springframework.data.domain.Pageable;

/**
 * Service dành cho cổng thông tin người thuê (tenant portal).
 */
public interface TenantPortalService {
    /**
     * Lấy dữ liệu dashboard của người thuê theo tenantId.
     */
    TenantDashboardResponse getDashboard(String tenantId);

    /**
     * Lấy danh sách hóa đơn của người thuê.
     */
    PageResponse<TenantInvoiceSummaryResponse> getMyInvoices(String tenantId, Pageable pageable);

    /**
     * Lấy chi tiết 1 hóa đơn của người thuê (có kiểm tra quyền sở hữu theo tenantId).
     */
    TenantInvoiceDetailResponse getInvoiceDetail(String tenantId, String invoiceId);

    /**
     * Lấy danh sách các yêu cầu bảo trì do người thuê tạo.
     */
    PageResponse<TenantMaintenanceResponse> getMyMaintenanceRequests(String tenantId, Pageable pageable);

    /**
     * Người thuê tạo yêu cầu bảo trì mới.
     */
    TenantMaintenanceResponse createMaintenanceRequest(String tenantId, TenantMaintenanceRequest request);

    /**
     * Lấy tổng quan chi phí tiện ích/dịch vụ của người thuê.
     */
    TenantUtilityOverviewResponse getUtilities(String tenantId);

    /**
     * Cập nhật trạng thái khai báo tạm trú của người thuê.
     */
    TenantUtilityOverviewResponse updateTemporaryResidence(String tenantId, TemporaryResidenceRequest request);

    /**
     * Đăng ký phương tiện của người thuê (xe máy/ô tô...).
     */
    TenantUtilityOverviewResponse registerVehicle(String tenantId, VehicleRegistrationRequest request);
}


