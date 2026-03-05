package com.company.base.dto.response;

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
    private Long id;
    private String roomId;
    private String tenantId;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal depositAmount;
    private BigDecimal actualRent;
    private String status;
}
