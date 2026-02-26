package com.company.base.dto.response;

import lombok.*;
import java.time.LocalDateTime;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private String role;
    private LocalDateTime createdAt;
}
