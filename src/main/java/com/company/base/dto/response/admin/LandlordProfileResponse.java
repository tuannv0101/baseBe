package com.company.base.dto.response.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Response DTO carrying output data for API operations.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LandlordProfileResponse {
    private Long id;
    private Long userId;
    private String username;
    private String email;
    private String businessName;
    private String contactPhone;
    private String status;
    private Boolean accountEnabled;
    private LocalDateTime createdAt;
    private String note;
}
