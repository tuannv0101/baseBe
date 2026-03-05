package com.company.base.dto.response.tenant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

/**
 * Response DTO carrying output data for API operations.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TenantUtilityOverviewResponse {
    private Boolean temporaryResidenceDeclared;
    private LocalDate temporaryResidenceDeclaredAt;
    private List<RuleDocumentItem> rules;
    private List<VehicleItem> vehicles;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RuleDocumentItem {
        private Long id;
        private String title;
        private Long fileId;
        private String fileUrl;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class VehicleItem {
        private Long id;
        private String vehicleType;
        private String plateNumber;
        private String status;
        private LocalDate registeredDate;
        private String note;
    }
}
