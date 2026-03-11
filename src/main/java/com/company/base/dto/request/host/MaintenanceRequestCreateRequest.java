package com.company.base.dto.request.host;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO carrying input data for API operations.
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MaintenanceRequestCreateRequest {
    private Long roomId;

    private Long tenantId;

    private String title;

    private String description;

    private String priority;
}
