package com.company.base.dto.response.host;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Response DTO carrying output data for API operations.
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentReceiptResponse {
    private String id;

    private String invoiceId;

    private String receiptCode;

    private String roomId;

    private String payerName;

    private BigDecimal amount;

    private String paymentMethod;

    private LocalDateTime paymentTime;

    private String note;
}

