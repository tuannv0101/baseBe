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
            createRoleIfNotFound("ROLE_USER", "Quyền người dùng thông thường");
            createRoleIfNotFound("ROLE_ADMIN", "Quyền quản trị viên");
            createRoleIfNotFound("ROLE_MANAGER", "Quyền quản lý");
        };
    }

    private void createRoleIfNotFound(String name, String description) {
        if (roleRepository.findByName(name).isEmpty()) {
            roleRepository.save(Role.builder().name(name).description(description).build());
        }
    }
}
