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
public class VehicleRegistrationRequest {
    private String vehicleType;

    private String plateNumber;

    private String note;
}
