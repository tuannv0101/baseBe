package com.company.base.service;

import com.company.base.dto.request.admin.AuthRequest;
import com.company.base.dto.response.admin.AuthResponse;

/**
 * Service xác thực: đăng ký và đăng nhập.
 */
public interface AuthService {
    /**
     * Đăng ký tài khoản mới theo thông tin đầu vào.
     */
    AuthResponse register(AuthRequest request);

    /**
     * Đăng nhập và trả về thông tin phiên/token theo thông tin xác thực.
     */
    AuthResponse login(AuthRequest request);
}
