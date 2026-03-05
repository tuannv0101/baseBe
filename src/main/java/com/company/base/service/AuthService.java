package com.company.base.service;

import com.company.base.dto.request.AuthRequest;
import com.company.base.dto.response.AuthResponse;
/**
 * Service contract defining operations for this module.
 */

public interface AuthService {
    AuthResponse register(AuthRequest request);
    AuthResponse login(AuthRequest request);
}
