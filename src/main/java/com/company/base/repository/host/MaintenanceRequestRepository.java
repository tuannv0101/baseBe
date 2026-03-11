package com.company.base.repository.host;

import com.company.base.entity.MaintenanceRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

/**
 * Repository for data access operations.
 */

public interface MaintenanceRequestRepository extends JpaRepository<MaintenanceRequest, Long> {
    List<MaintenanceRequest> findAllByOrderByRequestedAtDescIdDesc();

    List<MaintenanceRequest> findByStatusIgnoreCaseOrderByRequestedAtDescIdDesc(String status);

    List<MaintenanceRequest> findTop20ByStatusInOrderByRequestedAtDescIdDesc(Collection<String> statuses);

    List<MaintenanceRequest> findByTenantIdOrderByRequestedAtDescIdDesc(Long tenantId);

    Page<MaintenanceRequest> findAllByOrderByRequestedAtDescIdDesc(Pageable pageable);

    Page<MaintenanceRequest> findByStatusIgnoreCaseOrderByRequestedAtDescIdDesc(String status, Pageable pageable);

    Page<MaintenanceRequest> findByTenantIdOrderByRequestedAtDescIdDesc(Long tenantId, Pageable pageable);
}
