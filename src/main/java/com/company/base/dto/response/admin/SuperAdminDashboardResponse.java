package com.company.base.dto.response.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * Response DTO carrying output data for API operations.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SuperAdminDashboardResponse {
    private Long totalLandlords;

    private BigDecimal totalSubscriptionRevenue;

    private List<UserGrowthPoint> newUserGrowth;

    private ServerStatus serverStatus;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserGrowthPoint {
        private Integer month;

        private Integer year;

        private Long newUsers;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ServerStatus {
        private Long memoryUsedMb;

        private Long memoryMaxMb;

        private Double memoryUsagePercent;

        private String status;
    }
}
