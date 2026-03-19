package com.company.base.dto.response.host;

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
public class ContractResponse {
    private String id;

    private String contractCode;

    private String roomId;

    private String roomNumber;

    private String propertyName;

    private String tenantId;

    private String tenantName;

    private String tenantIdCardNumber;

    private LocalDate startDate;

    private LocalDate endDate;

    private BigDecimal depositAmount;

    private BigDecimal actualRent;

    private BigDecimal rentAmount;

    private String status;
}

