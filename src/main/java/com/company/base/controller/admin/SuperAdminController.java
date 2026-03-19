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
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.company.base.common.pagination.PageResponse;

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
    public ApiResponse<PageResponse<LandlordProfileResponse>> getLandlords(
            @RequestParam(required = false) String status,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        return ApiResponse.success(superAdminService.getLandlords(status, pageable));
    }

    @PutMapping("/landlords/{id}/approve")
    public ApiResponse<LandlordProfileResponse> approveLandlord(@PathVariable String id) {
        return ApiResponse.success(superAdminService.approveLandlord(id));
    }

    @PutMapping("/landlords/{id}/lock")
    public ApiResponse<LandlordProfileResponse> lockLandlord(@PathVariable String id, @RequestParam(required = false) String note) {
        return ApiResponse.success(superAdminService.lockLandlord(id, note));
    }

    @PutMapping("/landlords/{id}/unlock")
    public ApiResponse<LandlordProfileResponse> unlockLandlord(@PathVariable String id) {
        return ApiResponse.success(superAdminService.unlockLandlord(id));
    }

    @PostMapping("/plans")
    public ApiResponse<SubscriptionPlanResponse> createPlan(@RequestBody SubscriptionPlanRequest request) {
        return ApiResponse.success(superAdminService.createPlan(request));
    }

    @PutMapping("/plans/{id}")
    public ApiResponse<SubscriptionPlanResponse> updatePlan(@PathVariable String id, @RequestBody SubscriptionPlanRequest request) {
        return ApiResponse.success(superAdminService.updatePlan(id, request));
    }

    @GetMapping("/plans")
    public ApiResponse<PageResponse<SubscriptionPlanResponse>> getPlans(@PageableDefault(size = 20) Pageable pageable) {
        return ApiResponse.success(superAdminService.getPlans(pageable));
    }

    @PutMapping("/plans/{id}/delete")
    public ApiResponse<Void> deletePlan(@PathVariable String id) {
        superAdminService.deletePlan(id);
        return ApiResponse.success(null);
    }

    @PostMapping("/subscriptions")
    public ApiResponse<LandlordSubscriptionResponse> createSubscription(@RequestBody LandlordSubscriptionRequest request) {
        return ApiResponse.success(superAdminService.createSubscription(request));
    }

    @GetMapping("/subscriptions")
    public ApiResponse<PageResponse<LandlordSubscriptionResponse>> getSubscriptions(
            @RequestParam String landlordProfileId,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        return ApiResponse.success(superAdminService.getSubscriptions(landlordProfileId, pageable));
    }

    @PostMapping("/system-configs")
    public ApiResponse<SystemConfigResponse> upsertSystemConfig(@RequestBody SystemConfigRequest request) {
        return ApiResponse.success(superAdminService.upsertSystemConfig(request));
    }

    @GetMapping("/system-configs")
    public ApiResponse<PageResponse<SystemConfigResponse>> getSystemConfigs(@PageableDefault(size = 20) Pageable pageable) {
        return ApiResponse.success(superAdminService.getSystemConfigs(pageable));
    }

    @PostMapping("/tickets")
    public ApiResponse<SupportTicketResponse> createSupportTicket(@RequestBody SupportTicketRequest request) {
        return ApiResponse.success(superAdminService.createSupportTicket(request));
    }

    @PutMapping("/tickets/{id}")
    public ApiResponse<SupportTicketResponse> updateSupportTicket(@PathVariable String id, @RequestBody SupportTicketUpdateRequest request) {
        return ApiResponse.success(superAdminService.updateSupportTicket(id, request));
    }

    @GetMapping("/tickets")
    public ApiResponse<PageResponse<SupportTicketResponse>> getSupportTickets(
            @RequestParam(required = false) String status,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        return ApiResponse.success(superAdminService.getSupportTickets(status, pageable));
    }
}

