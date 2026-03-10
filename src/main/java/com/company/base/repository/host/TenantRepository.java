package com.company.base.repository.host;

import com.company.base.entity.Tenant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository for data access operations.
 */

public interface TenantRepository extends JpaRepository<Tenant, Long> {
    List<Tenant> findAllByOrderByFullNameAsc();

    List<Tenant> findByTemporaryResidenceDeclaredOrderByFullNameAsc(Boolean temporaryResidenceDeclared);

    Page<Tenant> findAllByOrderByFullNameAsc(Pageable pageable);

    Page<Tenant> findByTemporaryResidenceDeclaredOrderByFullNameAsc(Boolean temporaryResidenceDeclared, Pageable pageable);
}
