package com.company.base.dto.response.admin;

import lombok.*;

import java.time.LocalDateTime;
/**
 * Response DTO carrying output data for API operations.
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private String role;
    private LocalDateTime createdAt;
}
