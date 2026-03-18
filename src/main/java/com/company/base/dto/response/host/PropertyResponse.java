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
public class PropertyResponse {
    private Long id;

    private String name;

    private String address;

    private Integer totalFloors;

    // Room stats for this property (building)
    private Long occupiedRooms;     // đang cho thuê

    private Long maintenanceRooms;  // đang bảo trì

    private Long availableRooms;    // đang trống
}
