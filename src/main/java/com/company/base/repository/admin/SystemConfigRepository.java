package com.company.base.repository.admin;

import com.company.base.entity.SystemConfig;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for data access operations.
 */
public interface SystemConfigRepository extends JpaRepository<SystemConfig, Long> {
    Optional<SystemConfig> findByConfigKey(String configKey);
    List<SystemConfig> findAllByOrderByConfigKeyAsc();
}
