package com.company.base.service;

import com.company.base.dto.response.host.DashboardOverviewResponse;
import com.company.base.dto.response.host.SystemNotificationResponse;

import java.util.List;

/**
 * Service contract defining operations for this module.
 */

public interface DashboardService {
    DashboardOverviewResponse getOverview(Integer month, Integer year);

    List<SystemNotificationResponse> getSystemNotifications(Integer limit);
}
