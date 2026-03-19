package com.company.base.dto.response.host;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response DTO carrying output data for API operations.
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServiceUsageResponse {
    private String id;

    private String roomId;

    private String serviceId;

    private Integer month;

    private Integer year;

    private Double oldValue;

    private Double newValue;

    private Double consumption;
}

