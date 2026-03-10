package com.company.base.dto.response.host;

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
public class MaintenanceRequestResponse {
    private Long id;

    private String roomId;

    private String tenantId;

    private String title;

    private String description;

    private String priority;

    private String status;

    private String assignedTechnician;

    private LocalDateTime requestedAt;

    private LocalDateTime completedAt;

    private String note;
}
