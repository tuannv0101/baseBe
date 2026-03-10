package com.company.base.dto.response.host;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Response DTO carrying output data for API operations.
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContractLiquidationResponse {
    private Long id;

    private Long contractId;

    private String roomId;

    private String tenantId;

    private LocalDate liquidationDate;

    private String reason;

    private String note;
}
