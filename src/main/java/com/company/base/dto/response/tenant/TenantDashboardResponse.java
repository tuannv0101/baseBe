package com.company.base.dto.response.tenant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Response DTO carrying output data for API operations.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TenantDashboardResponse {
    private String tenantId;

    private String roomId;

    private BigDecimal amountDueNow;

    private LocalDate nearestDueDate;

    private List<AnnouncementItem> latestAnnouncements;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AnnouncementItem {
        private String id;

        private String title;

        private String content;

        private LocalDateTime createdAt;
    }
}

