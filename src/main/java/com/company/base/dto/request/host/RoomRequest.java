package com.company.base.dto.request.host;

import com.company.base.dto.request.host.roomManager.RoomAssetCreateReqDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomRequest {
    private String propertyId;

    private Float area;

    private Integer floor;

    private String price;

    private String roomNumber;

    private String status;

    private String type;

    // If provided (even empty), assets will be replaced.
    private List<RoomAssetCreateReqDTO> roomAssetCreateReqDTOS;
}

