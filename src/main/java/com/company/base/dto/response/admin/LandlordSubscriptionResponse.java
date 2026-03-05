package com.company.base.dto.response.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Response DTO carrying output data for API operations.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LandlordSubscriptionResponse {
    private Long id;
    private Long landlordProfileId;
    private Long planId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private BigDecimal amountPaid;
}
