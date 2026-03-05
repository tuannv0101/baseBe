package com.company.base.repository.host;

import com.company.base.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

/**
 * Repository for data access operations.
 */

public interface ContractRepository extends JpaRepository<Contract, Long> {
    List<Contract> findByStatusIgnoreCaseOrderByStartDateDescIdDesc(String status);

    List<Contract> findByStatusInOrderByStartDateDescIdDesc(Collection<String> statuses);

    List<Contract> findByEndDateBetweenAndStatusIgnoreCaseOrderByEndDateAscIdDesc(LocalDate fromDate, LocalDate toDate, String status);
}
