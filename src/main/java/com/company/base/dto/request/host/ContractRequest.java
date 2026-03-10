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
public class ContractRequest {
    private String roomId;

    private String tenantId;

    private LocalDate startDate;

    private LocalDate endDate;

    private BigDecimal depositAmount;

    private BigDecimal actualRent;

    private String status;
}
