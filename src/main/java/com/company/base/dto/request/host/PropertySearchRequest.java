package com.company.base.dto.request.host;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PropertySearchRequest {
    private String nameProperty;

    private String tenantName;

    private String statusRoom;

    private String priceRoom;

    private String typeRoom;

}
