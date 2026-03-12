package com.company.base.dto.response.host.roomManager;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ListRoomResDTO {
    private Long id;

    private String roomNumber;

    private String propertyName;

    private String typeRoom;

    private String price;

    private String tenantName;

    private String tenantIdCardNumber;

    private String statusRoom;
}
