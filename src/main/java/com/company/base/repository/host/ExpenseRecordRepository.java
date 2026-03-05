package com.company.base.repository.host;

import com.company.base.entity.ExpenseRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository for data access operations.
 */

public interface ExpenseRecordRepository extends JpaRepository<ExpenseRecord, Long> {
    List<ExpenseRecord> findAllByOrderByExpenseDateDescIdDesc();

    List<ExpenseRecord> findByExpenseDateBetweenOrderByExpenseDateDescIdDesc(LocalDate fromDate, LocalDate toDate);
}
