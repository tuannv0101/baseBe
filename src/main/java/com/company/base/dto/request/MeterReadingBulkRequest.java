package com.company.base.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
/**
 * Request DTO carrying input data for API operations.
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MeterReadingBulkRequest {
    private String serviceId;
    private Integer month;
    private Integer year;
    private List<ReadingRow> readings;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReadingRow {
        private String roomId;
        private Double oldValue;
        private Double newValue;
    }
}
