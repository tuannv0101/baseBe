package com.company.base.dto.response.admin;

import lombok.*;
/**
 * Response DTO carrying output data for API operations.
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private String token;
}
