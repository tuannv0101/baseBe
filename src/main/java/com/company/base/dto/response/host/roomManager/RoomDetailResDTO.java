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
    private Long roomId;

    private Long propertyId;
    private String propertyName;
    private String propertyAddress;

    private Long tenantId;
    private String tenantFullName;

    private Float area;
    private Integer floor;
    private String price;
    private String roomNumber;
    private String status;
    private String type;

    private List<RoomAssetDetailResDTO> assets;
}
