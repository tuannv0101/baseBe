package com.company.base.service;

import com.company.base.dto.request.AuthRequest;
import com.company.base.dto.response.AuthResponse;

public interface AuthService {
    AuthResponse register(AuthRequest request);
    AuthResponse login(AuthRequest request);
}
