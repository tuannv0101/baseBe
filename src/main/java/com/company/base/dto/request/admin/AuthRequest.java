package com.company.base.dto.request.admin;

import lombok.*;
/**
 * Request DTO carrying input data for API operations.
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {
    private String username;
    private String password;
    private String role;
}
