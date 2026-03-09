package com.company.base.config;

import com.company.base.entity.Role;
import com.company.base.repository.admin.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final RoleRepository roleRepository;

    @Bean
    public CommandLineRunner init() {
        return args -> {
            createRoleIfNotFound("ROLE_TENANT", "Người thuê trọ");
            createRoleIfNotFound("ROLE_HOST", "Chủ trọ");
            createRoleIfNotFound("ROLE_ADMIN", "Quảng trị viên");
            createRoleIfNotFound("ROLE_SUPER_ADMIN", "System owner role");
        };
    }

    private void createRoleIfNotFound(String name, String description) {
        if (roleRepository.findByName(name).isEmpty()) {
            roleRepository.save(Role.builder().name(name).description(description).build());
        }
    }
}
