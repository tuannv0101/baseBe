package com.company.base.dto.response.host;

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
public class RoomMatrixResponse {
    private Long propertyId;
    private String propertyName;
    private String address;
    private List<FloorRow> floors;
    private Summary summary;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FloorRow {
        private Integer floor;
        private List<RoomCell> rooms;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RoomCell {
        private Long id;
        private String roomNumber;
        private String status;
        private String price;
        private Float area;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Summary {
        private int totalRooms;
        private int availableRooms;
        private int occupiedRooms;
        private int maintenanceRooms;
    }
}
