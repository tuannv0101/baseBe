package com.company.base.dto.request.host;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
/**
 * Request DTO carrying input data for API operations.
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssetMaintenanceHistoryRequest {
    private LocalDate maintenanceDate;
    private String maintenanceType;
    private String description;
    private String vendor;
    private BigDecimal cost;
    private String status;
    private String note;
}
