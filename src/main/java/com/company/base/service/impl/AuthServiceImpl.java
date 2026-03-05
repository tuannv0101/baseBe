package com.company.base.service.impl;

import com.company.base.dto.request.admin.AuthRequest;
import com.company.base.dto.response.admin.AuthResponse;
import com.company.base.entity.User;
import com.company.base.entity.LandlordProfile;
import com.company.base.entity.Role;
import com.company.base.exception.AppException;
import com.company.base.repository.admin.LandlordProfileRepository;
import com.company.base.repository.admin.RoleRepository;
import com.company.base.repository.admin.UserRepository;
import com.company.base.security.JwtService;
import com.company.base.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;

import java.util.Collections;

/**
 * Service implementation containing business logic for this module.
 */

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final LandlordProfileRepository landlordProfileRepository;

    @Override
    public AuthResponse register(AuthRequest request) {
        String roleName = request.getRole();
        if (roleName == null || roleName.isEmpty()) {
            roleName = "ROLE_USER";
        } else if (!roleName.startsWith("ROLE_")) {
            roleName = "ROLE_" + roleName.toUpperCase();
        }

        Role userRole = roleRepository.findByName(roleName)
                .orElseThrow(() -> new AppException(HttpStatus.BAD_REQUEST.value(), "Role is not found"));

        boolean isLandlordRole = "ROLE_MANAGER".equals(roleName);

        var user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Collections.singleton(userRole))
                .provider(User.Provider.LOCAL)
                .enabled(!isLandlordRole)
                .build();
        User savedUser = userRepository.save(user);

        if (isLandlordRole) {
            LandlordProfile profile = new LandlordProfile();
            profile.setUserId(savedUser.getId());
            profile.setBusinessName(savedUser.getUsername());
            profile.setStatus("PENDING");
            landlordProfileRepository.save(profile);
        }

        return AuthResponse.builder().token(jwtService.generateToken(user)).build();
    }

    @Override
    public AuthResponse login(AuthRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        var user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        return AuthResponse.builder().token(jwtService.generateToken(user)).build();
    }
}
