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
}
