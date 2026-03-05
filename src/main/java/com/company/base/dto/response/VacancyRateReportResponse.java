package com.company.base.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
/**
 * Response DTO carrying output data for API operations.
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VacancyRateReportResponse {
    private Integer fromMonth;
    private Integer fromYear;
    private Integer toMonth;
    private Integer toYear;
    private Long totalRooms;
    private Double averageVacancyRate;
    private List<VacancyPoint> points;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class VacancyPoint {
        private Integer month;
        private Integer year;
        private Long totalRooms;
        private Long occupiedRooms;
        private Long vacantRooms;
        private Double vacancyRate;
    }
}
