package com.company.base.dto.response;

import lombok.*;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class AuthResponse {
    private String token;
}
