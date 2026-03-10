package com.company.base.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

@Configuration
public class AuditingConfig {
    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth == null || !auth.isAuthenticated()) {
                return Optional.empty();
            }

            Object principal = auth.getPrincipal();
            if (principal == null || "anonymousUser".equals(principal)) {
                return Optional.empty();
            }

            if (principal instanceof UserDetails ud) {
                return Optional.ofNullable(ud.getUsername());
            }

            if (principal instanceof String s && !s.isBlank()) {
                return Optional.of(s);
            }

            return Optional.empty();
        };
    }
}

