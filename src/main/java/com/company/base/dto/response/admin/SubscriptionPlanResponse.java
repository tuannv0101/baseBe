package com.company.base.dto.response.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Response DTO carrying output data for API operations.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionPlanResponse {
    private Long id;

    private String code;

    private String name;

    private Integer maxRooms;

    private BigDecimal monthlyPrice;

    private Boolean active;

    private String description;
}
