package com.company.base.controller.admin;

import com.company.base.common.ApiResponse;
import com.company.base.dto.request.admin.LandlordSubscriptionRequest;
import com.company.base.dto.request.admin.SubscriptionPlanRequest;
import com.company.base.dto.request.admin.SupportTicketRequest;
import com.company.base.dto.request.admin.SupportTicketUpdateRequest;
import com.company.base.dto.request.admin.SystemConfigRequest;
import com.company.base.dto.response.admin.LandlordProfileResponse;
import com.company.base.dto.response.admin.LandlordSubscriptionResponse;
import com.company.base.dto.response.admin.SubscriptionPlanResponse;
import com.company.base.dto.response.admin.SuperAdminDashboardResponse;
import com.company.base.dto.response.admin.SupportTicketResponse;
import com.company.base.dto.response.admin.SystemConfigResponse;
import com.company.base.service.SuperAdminService;
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
@RequestMapping("/api/v1/super-admin")
@RequiredArgsConstructor
public class SuperAdminController {

    private final SuperAdminService superAdminService;

    @GetMapping("/dashboard")
    public ApiResponse<SuperAdminDashboardResponse> getDashboard(
            @RequestParam(required = false) Integer fromMonth,
            @RequestParam(required = false) Integer fromYear,
            @RequestParam(required = false) Integer toMonth,
            @RequestParam(required = false) Integer toYear
    ) {
        return ApiResponse.success(superAdminService.getDashboard(fromMonth, fromYear, toMonth, toYear));
    }

    @GetMapping("/landlords")
    public ApiResponse<List<LandlordProfileResponse>> getLandlords(@RequestParam(required = false) String status) {
        return ApiResponse.success(superAdminService.getLandlords(status));
    }

    @PutMapping("/landlords/{id}/approve")
    public ApiResponse<LandlordProfileResponse> approveLandlord(@PathVariable Long id) {
        return ApiResponse.success(superAdminService.approveLandlord(id));
    }

    @PutMapping("/landlords/{id}/lock")
    public ApiResponse<LandlordProfileResponse> lockLandlord(@PathVariable Long id, @RequestParam(required = false) String note) {
        return ApiResponse.success(superAdminService.lockLandlord(id, note));
    }

    @PutMapping("/landlords/{id}/unlock")
    public ApiResponse<LandlordProfileResponse> unlockLandlord(@PathVariable Long id) {
        return ApiResponse.success(superAdminService.unlockLandlord(id));
    }

    @PostMapping("/plans")
    public ApiResponse<SubscriptionPlanResponse> createPlan(@RequestBody SubscriptionPlanRequest request) {
        return ApiResponse.success(superAdminService.createPlan(request));
    }

    @PutMapping("/plans/{id}")
    public ApiResponse<SubscriptionPlanResponse> updatePlan(@PathVariable Long id, @RequestBody SubscriptionPlanRequest request) {
        return ApiResponse.success(superAdminService.updatePlan(id, request));
    }

    @GetMapping("/plans")
    public ApiResponse<List<SubscriptionPlanResponse>> getPlans() {
        return ApiResponse.success(superAdminService.getPlans());
    }

    @DeleteMapping("/plans/{id}")
    public ApiResponse<Void> deletePlan(@PathVariable Long id) {
        superAdminService.deletePlan(id);
        return ApiResponse.success(null);
    }

    @PostMapping("/subscriptions")
    public ApiResponse<LandlordSubscriptionResponse> createSubscription(@RequestBody LandlordSubscriptionRequest request) {
        return ApiResponse.success(superAdminService.createSubscription(request));
    }

    @GetMapping("/subscriptions")
    public ApiResponse<List<LandlordSubscriptionResponse>> getSubscriptions(@RequestParam Long landlordProfileId) {
        return ApiResponse.success(superAdminService.getSubscriptions(landlordProfileId));
    }

    @PostMapping("/system-configs")
    public ApiResponse<SystemConfigResponse> upsertSystemConfig(@RequestBody SystemConfigRequest request) {
        return ApiResponse.success(superAdminService.upsertSystemConfig(request));
    }

    @GetMapping("/system-configs")
    public ApiResponse<List<SystemConfigResponse>> getSystemConfigs() {
        return ApiResponse.success(superAdminService.getSystemConfigs());
    }

    @PostMapping("/tickets")
    public ApiResponse<SupportTicketResponse> createSupportTicket(@RequestBody SupportTicketRequest request) {
        return ApiResponse.success(superAdminService.createSupportTicket(request));
    }

    @PutMapping("/tickets/{id}")
    public ApiResponse<SupportTicketResponse> updateSupportTicket(@PathVariable Long id, @RequestBody SupportTicketUpdateRequest request) {
        return ApiResponse.success(superAdminService.updateSupportTicket(id, request));
    }

    @GetMapping("/tickets")
    public ApiResponse<List<SupportTicketResponse>> getSupportTickets(@RequestParam(required = false) String status) {
        return ApiResponse.success(superAdminService.getSupportTickets(status));
    }
}
