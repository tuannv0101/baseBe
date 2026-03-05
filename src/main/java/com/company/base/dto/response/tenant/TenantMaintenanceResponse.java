package com.company.base.dto.response.tenant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Response DTO carrying output data for API operations.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TenantMaintenanceResponse {
    private Long id;
    private String title;
    private String description;
    private String priority;
    private String status;
    private String assignedTechnician;
    private Long attachmentFileId;
    private String attachmentUrl;
    private LocalDateTime requestedAt;
    private LocalDateTime completedAt;
    private String note;
}
