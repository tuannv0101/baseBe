package com.company.base.dto.request.host;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PropertyServiceBulkUpdateRequest {
    private String serviceId;

    private String name;

    private BigDecimal unitPrice;

    private String unitType;
}

