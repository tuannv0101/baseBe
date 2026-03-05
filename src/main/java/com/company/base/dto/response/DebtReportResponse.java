package com.company.base.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
/**
 * Response DTO carrying output data for API operations.
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DebtReportResponse {
    private LocalDate reportDate;
    private BigDecimal totalOutstanding;
    private BigDecimal totalOverdue;
    private List<DebtItem> items;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DebtItem {
        private Long invoiceId;
        private String invoiceCode;
        private String contractId;
        private String tenantId;
        private String roomId;
        private BigDecimal amount;
        private String status;
        private LocalDate dueDate;
        private Long daysPastDue;
    }
}
