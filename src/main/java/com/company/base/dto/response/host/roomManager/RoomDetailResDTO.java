package com.company.base.dto.response.host.roomManager;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomDetailResDTO {
    private String roomId;

    private String propertyId;
    private String propertyName;
    private String propertyAddress;

    private String tenantId;
    private String tenantFullName;

    private Float area;
    private Integer floor;
    private String price;
    private String roomNumber;
    private String status;
    private String type;

    private List<RoomAssetDetailResDTO> assets;
}

