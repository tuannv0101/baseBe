package com.company.base.controller.tenant;

import com.company.base.common.ApiResponse;
import com.company.base.dto.request.tenant.TemporaryResidenceRequest;
import com.company.base.dto.request.tenant.TenantMaintenanceRequest;
import com.company.base.dto.request.tenant.VehicleRegistrationRequest;
import com.company.base.dto.response.tenant.TenantDashboardResponse;
import com.company.base.dto.response.tenant.TenantInvoiceDetailResponse;
import com.company.base.dto.response.tenant.TenantInvoiceSummaryResponse;
import com.company.base.dto.response.tenant.TenantMaintenanceResponse;
import com.company.base.dto.response.tenant.TenantUtilityOverviewResponse;
import com.company.base.service.TenantPortalService;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/api/v1/tenant")
@RequiredArgsConstructor
public class TenantPortalController {

    private final TenantPortalService tenantPortalService;

    @GetMapping("/dashboard")
    public ApiResponse<TenantDashboardResponse> getDashboard(@RequestParam String tenantId) {
        return ApiResponse.success(tenantPortalService.getDashboard(tenantId));
    }

    @GetMapping("/invoices")
    public ApiResponse<List<TenantInvoiceSummaryResponse>> getMyInvoices(@RequestParam String tenantId) {
        return ApiResponse.success(tenantPortalService.getMyInvoices(tenantId));
    }

    @GetMapping("/invoices/{invoiceId}")
    public ApiResponse<TenantInvoiceDetailResponse> getInvoiceDetail(
            @RequestParam String tenantId,
            @PathVariable Long invoiceId
    ) {
        return ApiResponse.success(tenantPortalService.getInvoiceDetail(tenantId, invoiceId));
    }

    @GetMapping("/maintenance")
    public ApiResponse<List<TenantMaintenanceResponse>> getMyMaintenanceRequests(@RequestParam String tenantId) {
        return ApiResponse.success(tenantPortalService.getMyMaintenanceRequests(tenantId));
    }

    @PostMapping("/maintenance")
    public ApiResponse<TenantMaintenanceResponse> createMaintenanceRequest(
            @RequestParam String tenantId,
            @RequestBody TenantMaintenanceRequest request
    ) {
        return ApiResponse.success(tenantPortalService.createMaintenanceRequest(tenantId, request));
    }

    @GetMapping("/utilities")
    public ApiResponse<TenantUtilityOverviewResponse> getUtilities(@RequestParam String tenantId) {
        return ApiResponse.success(tenantPortalService.getUtilities(tenantId));
    }

    @PutMapping("/utilities/temporary-residence")
    public ApiResponse<TenantUtilityOverviewResponse> updateTemporaryResidence(
            @RequestParam String tenantId,
            @RequestBody TemporaryResidenceRequest request
    ) {
        return ApiResponse.success(tenantPortalService.updateTemporaryResidence(tenantId, request));
    }

    @PostMapping("/utilities/vehicles")
    public ApiResponse<TenantUtilityOverviewResponse> registerVehicle(
            @RequestParam String tenantId,
            @RequestBody VehicleRegistrationRequest request
    ) {
        return ApiResponse.success(tenantPortalService.registerVehicle(tenantId, request));
    }
}
