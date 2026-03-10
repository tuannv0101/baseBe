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
public class RevenueReportResponse {
    private Integer fromMonth;

    private Integer fromYear;

    private Integer toMonth;

    private Integer toYear;

    private BigDecimal totalExpected;

    private BigDecimal totalActual;

    private BigDecimal variance;

    private List<RevenuePoint> points;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RevenuePoint {
        private Integer month;

        private Integer year;

        private BigDecimal expected;

        private BigDecimal actual;

        private BigDecimal variance;
    }
}
