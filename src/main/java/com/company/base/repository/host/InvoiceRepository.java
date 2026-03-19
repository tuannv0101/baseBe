package com.company.base.repository.host;

import com.company.base.entity.InvoiceManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

/**
 * Repository for data access operations.
 */

public interface InvoiceRepository extends JpaRepository<InvoiceManager, String> {
    List<InvoiceManager> findByDueDateBetweenOrderByDueDateAscIdDesc(LocalDate fromDate, LocalDate toDate);

    List<InvoiceManager> findByStatusInAndDueDateBeforeOrderByDueDateAscIdDesc(Collection<String> statuses, LocalDate dueDate);

    List<InvoiceManager> findByStatusInOrderByDueDateAscIdDesc(Collection<String> statuses);

    List<InvoiceManager> findByContractIdOrderByDueDateDescIdDesc(String contractId);

    List<InvoiceManager> findByContractIdInOrderByDueDateDescIdDesc(Collection<String> contractIds);

    Page<InvoiceManager> findByDueDateBetweenOrderByDueDateAscIdDesc(LocalDate fromDate, LocalDate toDate, Pageable pageable);

    Page<InvoiceManager> findByStatusInAndDueDateBeforeOrderByDueDateAscIdDesc(Collection<String> statuses, LocalDate dueDate, Pageable pageable);

    Page<InvoiceManager> findByContractIdInOrderByDueDateDescIdDesc(Collection<String> contractIds, Pageable pageable);
}

