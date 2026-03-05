package com.company.base.dto.request.admin;

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
public class SystemConfigRequest {
    private String configKey;
    private String configValue;
    private String description;
}
