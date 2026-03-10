package com.company.base.dto.request.admin;

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
public class LandlordSubscriptionRequest {
    private Long landlordProfileId;

    private Long planId;

    private LocalDate startDate;

    private LocalDate endDate;

    private String status;

    private BigDecimal amountPaid;
}
