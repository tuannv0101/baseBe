package com.company.base.dto.response.host.roomManager;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomAssetDetailResDTO {
    private Long id;
    private String name;
    private String brand;
    private String serialNumber;
    private String status;
}

