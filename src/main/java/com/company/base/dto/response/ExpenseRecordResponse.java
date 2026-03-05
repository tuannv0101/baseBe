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
public class ExpenseRecordResponse {
    private Long id;
    private LocalDate expenseDate;
    private String category;
    private BigDecimal amount;
    private String description;
    private String vendor;
    private String paidBy;
    private String note;
}
