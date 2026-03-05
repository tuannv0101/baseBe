package com.company.base.service;

import com.company.base.dto.request.admin.AuthRequest;
import com.company.base.dto.response.admin.AuthResponse;

/**
 * Service contract defining operations for this module.
 */

public interface AuthService {
    AuthResponse register(AuthRequest request);

    AuthResponse login(AuthRequest request);
}
