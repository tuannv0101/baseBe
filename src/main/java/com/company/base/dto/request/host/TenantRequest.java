package com.company.base.dto.request.host;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Request DTO carrying input data for API operations.
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TenantRequest {
    private String fullName;

    private String phone;

    private String email;

    private String idCardNumber;

    private Long portraitImageId;

    private Boolean temporaryResidenceDeclared;

    private LocalDate temporaryResidenceDeclaredAt;
}
