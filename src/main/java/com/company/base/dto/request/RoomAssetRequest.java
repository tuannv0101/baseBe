package com.company.base.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * Request DTO carrying input data for API operations.
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomAssetRequest {
    private String roomId;
    private String categoryId;
    private String serialNumber;
    private String status;
}
