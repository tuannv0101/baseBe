package com.company.base.dto.request.host.roomManager;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomManagerCreateReqDTO {
    private String propertiesId;

    private Float area;

    private Integer floor;

    private String price;

    private String roomNumber;

    private String status;

    private String typeRoom;

    private List<RoomAssetCreateReqDTO> roomAssetCreateReqDTOS;
}

