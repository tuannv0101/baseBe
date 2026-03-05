package com.company.base.dto.request.host;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
/**
 * Request DTO carrying input data for API operations.
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseRecordRequest {
    private LocalDate expenseDate;
    private String category;
    private BigDecimal amount;
    private String description;
    private String vendor;
    private String paidBy;
    private String note;
}
