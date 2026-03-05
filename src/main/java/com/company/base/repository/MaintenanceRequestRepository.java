package com.company.base.repository;

import com.company.base.entity.MaintenanceRequest;
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
}
