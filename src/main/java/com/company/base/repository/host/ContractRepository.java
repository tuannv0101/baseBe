package com.company.base.repository.host;

import com.company.base.entity.Contract;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Repository for data access operations.
 */

public interface ContractRepository extends JpaRepository<Contract, Long> {
    List<Contract> findByStatusIgnoreCaseOrderByStartDateDescIdDesc(String status);

    List<Contract> findByStatusInOrderByStartDateDescIdDesc(Collection<String> statuses);

    List<Contract> findByEndDateBetweenAndStatusIgnoreCaseOrderByEndDateAscIdDesc(LocalDate fromDate, LocalDate toDate, String status);

    List<Contract> findByTenantIdOrderByStartDateDescIdDesc(Long tenantId);

    Optional<Contract> findFirstByTenantIdAndStatusIgnoreCaseOrderByStartDateDescIdDesc(Long tenantId, String status);

    Optional<Contract> findFirstByRoomIdAndStatusIgnoreCaseOrderByStartDateDescIdDesc(Long roomId, String status);

    Page<Contract> findAllByOrderByStartDateDescIdDesc(Pageable pageable);

    Page<Contract> findByStatusIgnoreCaseOrderByStartDateDescIdDesc(String status, Pageable pageable);

    Page<Contract> findByStatusInOrderByStartDateDescIdDesc(Collection<String> statuses, Pageable pageable);
}
