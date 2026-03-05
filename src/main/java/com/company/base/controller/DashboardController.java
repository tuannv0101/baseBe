package com.company.base.controller;

import com.company.base.common.ApiResponse;
import com.company.base.dto.response.DashboardOverviewResponse;
import com.company.base.dto.response.SystemNotificationResponse;
import com.company.base.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
/**
 * REST controller that exposes API endpoints for this module.
 */

@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/overview")
    public ApiResponse<DashboardOverviewResponse> getOverview(
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer year
    ) {
        return ApiResponse.success(dashboardService.getOverview(month, year));
    }

    @GetMapping("/notifications")
    public ApiResponse<List<SystemNotificationResponse>> getSystemNotifications(
            @RequestParam(required = false) Integer limit
    ) {
        return ApiResponse.success(dashboardService.getSystemNotifications(limit));
    }
}
