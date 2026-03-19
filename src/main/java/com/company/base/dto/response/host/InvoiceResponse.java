package com.company.base.dto.response.host;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Response DTO carrying output data for API operations.
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceResponse {
    private String id;

    private String contractId;

    private String invoiceCode;

    private BigDecimal totalAmount;

    private String status;

    private LocalDate dueDate;

    private LocalDateTime paymentDate;
}

