package com.company.base.service;

import com.company.base.dto.response.host.DashboardOverviewResponse;
import com.company.base.dto.response.host.SystemNotificationResponse;

import java.util.List;

/**
 * Service tổng hợp số liệu cho dashboard (tổng quan và thông báo hệ thống).
 */
public interface DashboardService {
    /**
     * Lấy dữ liệu tổng quan dashboard theo tháng/năm.
     */
    DashboardOverviewResponse getOverview(Integer month, Integer year);

    /**
     * Lấy danh sách thông báo hệ thống gần nhất.
     */
    List<SystemNotificationResponse> getSystemNotifications(Integer limit);
}
