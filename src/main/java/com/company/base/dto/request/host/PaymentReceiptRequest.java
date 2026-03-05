package com.company.base.dto.request.host;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
/**
 * Request DTO carrying input data for API operations.
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentReceiptRequest {
    private Long invoiceId;
    private String receiptCode;
    private String roomId;
    private String payerName;
    private BigDecimal amount;
    private String paymentMethod;
    private LocalDateTime paymentTime;
    private String note;
}
