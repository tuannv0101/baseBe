package com.company.base.repository.tenant;

import com.company.base.entity.TenantVehicleRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository for data access operations.
 */
public interface TenantVehicleRegistrationRepository extends JpaRepository<TenantVehicleRegistration, Long> {
    List<TenantVehicleRegistration> findByTenantIdOrderByRegisteredDateDescIdDesc(Long tenantId);
}
