package com.company.base.dto.response.host;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomResponse {
    private Long roomId;

    private String propertyId;

    private Float area;

    private Integer floor;

    private String price;

    private String roomNumber;

    private String status;
}
