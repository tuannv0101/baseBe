package com.company.base.dto.request.host.roomManager;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomAssetCreateReqDTO {
    // ID phòng sở hữu tài sản.
    private String roomId;
    // Tên danh mục thiết bị.
    private String name;
    // Thương hiệu thiết bị.
    private String brand;
    // Số serial của tài sản.
    private String serialNumber;
    // Trạng thái tài sản: NEW, GOOD, BROKEN, REPAIRING.
    private String status; // NEW, GOOD, BROKEN, REPAIRING
}

