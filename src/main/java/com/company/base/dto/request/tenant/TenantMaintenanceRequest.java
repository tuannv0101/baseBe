package com.company.base.dto.request.tenant;

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
public class TenantMaintenanceRequest {
    private String title;

    private String description;

    private String priority;

    private Long attachmentFileId;
}
