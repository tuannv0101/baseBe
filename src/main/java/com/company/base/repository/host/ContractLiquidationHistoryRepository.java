package com.company.base.repository.host;

import com.company.base.entity.ContractLiquidationHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository for data access operations.
 */

public interface ContractLiquidationHistoryRepository extends JpaRepository<ContractLiquidationHistory, Long> {
    List<ContractLiquidationHistory> findAllByOrderByLiquidationDateDescIdDesc();

    Page<ContractLiquidationHistory> findAllByOrderByLiquidationDateDescIdDesc(Pageable pageable);
}
