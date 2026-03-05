package com.company.base.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
/**
 * Response DTO carrying output data for API operations.
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TenantResponse {
    private Long id;
    private String fullName;
    private String phone;
    private String email;
    private String idCardNumber;
    private Long portraitImageId;
    private String portraitImageUrl;
    private Boolean temporaryResidenceDeclared;
    private LocalDate temporaryResidenceDeclaredAt;
}
