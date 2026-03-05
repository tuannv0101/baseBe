package com.company.base.repository;

import com.company.base.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
/**
 * Repository for data access operations.
 */

public interface TenantRepository extends JpaRepository<Tenant, Long> {
    List<Tenant> findAllByOrderByFullNameAsc();
    List<Tenant> findByTemporaryResidenceDeclaredOrderByFullNameAsc(Boolean temporaryResidenceDeclared);
}
