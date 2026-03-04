package com.company.base.dto.request;

import lombok.*;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class AuthRequest {
    private String username;
    private String password;
    private String role;
}
