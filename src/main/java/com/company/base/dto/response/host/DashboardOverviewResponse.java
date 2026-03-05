package com.company.base.dto.response.host;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
/**
 * Response DTO carrying output data for API operations.
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DashboardOverviewResponse {
    private Integer month;
    private Integer year;
    private BigDecimal actualRevenue;
    private BigDecimal totalIncome;
    private BigDecimal totalExpense;
    private BigDecimal netCashFlow;
    private Long totalRooms;
    private Long occupiedRooms;
    private Long vacantRooms;
    private Double occupancyRate;
    private List<CashFlowPoint> cashFlowChart;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CashFlowPoint {
        private Integer month;
        private Integer year;
        private BigDecimal income;
        private BigDecimal expense;
        private BigDecimal net;
    }
}
