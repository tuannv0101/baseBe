package com.company.base.dto.request.host;

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
public class BillingServiceRequest {
    private String name;
    private BigDecimal unitPrice;
    private String unitType;
}
