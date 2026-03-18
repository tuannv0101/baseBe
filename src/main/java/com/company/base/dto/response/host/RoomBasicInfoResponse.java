package com.company.base.dto.response.host;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Basic room info for lightweight listings (used by /room-matrix endpoint).
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomBasicInfoResponse {
    private Long roomId;

    private Long propertyId;

    private Integer floor;

    private String roomNumber;

    private String status;

    private String price;

    private Float area;

    private String typeRoom;

    private String tenantName;

    private String tenantIdCardNumber;
}
