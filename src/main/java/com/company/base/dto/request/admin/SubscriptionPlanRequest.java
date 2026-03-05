package com.company.base.dto.request.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Request DTO carrying input data for API operations.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionPlanRequest {
    private String code;
    private String name;
    private Integer maxRooms;
    private BigDecimal monthlyPrice;
    private Boolean active;
    private String description;
}
