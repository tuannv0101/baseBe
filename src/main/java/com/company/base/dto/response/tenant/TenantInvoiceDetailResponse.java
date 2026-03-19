package com.company.base.dto.response.tenant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Response DTO carrying output data for API operations.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TenantInvoiceDetailResponse {
    private String id;

    private String invoiceCode;

    private String contractId;

    private BigDecimal totalAmount;

    private String status;

    private LocalDate dueDate;

    private LocalDateTime paymentDate;

    private List<ServiceItem> services;

    private List<PaymentItem> paymentHistory;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ServiceItem {
        private String serviceId;

        private String serviceName;

        private String unitType;

        private BigDecimal unitPrice;

        private Double oldValue;

        private Double newValue;

        private Double consumption;

        private BigDecimal estimatedAmount;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PaymentItem {
        private String receiptId;

        private String receiptCode;

        private BigDecimal amount;

        private String paymentMethod;

        private LocalDateTime paymentTime;
    }
}

