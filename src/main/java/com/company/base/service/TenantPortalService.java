package com.company.base.service;

import com.company.base.dto.request.tenant.TemporaryResidenceRequest;
import com.company.base.dto.request.tenant.TenantMaintenanceRequest;
import com.company.base.dto.request.tenant.VehicleRegistrationRequest;
import com.company.base.dto.response.tenant.TenantDashboardResponse;
import com.company.base.dto.response.tenant.TenantInvoiceDetailResponse;
import com.company.base.dto.response.tenant.TenantInvoiceSummaryResponse;
import com.company.base.dto.response.tenant.TenantMaintenanceResponse;
import com.company.base.dto.response.tenant.TenantUtilityOverviewResponse;

import java.util.List;

/**
 * Service contract defining operations for this module.
 */
public interface TenantPortalService {
    TenantDashboardResponse getDashboard(String tenantId);
    List<TenantInvoiceSummaryResponse> getMyInvoices(String tenantId);
    TenantInvoiceDetailResponse getInvoiceDetail(String tenantId, Long invoiceId);
    List<TenantMaintenanceResponse> getMyMaintenanceRequests(String tenantId);
    TenantMaintenanceResponse createMaintenanceRequest(String tenantId, TenantMaintenanceRequest request);
    TenantUtilityOverviewResponse getUtilities(String tenantId);
    TenantUtilityOverviewResponse updateTemporaryResidence(String tenantId, TemporaryResidenceRequest request);
    TenantUtilityOverviewResponse registerVehicle(String tenantId, VehicleRegistrationRequest request);
}
