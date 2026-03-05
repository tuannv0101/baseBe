package com.company.base.repository.host;

import com.company.base.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

/**
 * Repository for data access operations.
 */

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    List<Invoice> findByDueDateBetweenOrderByDueDateAscIdDesc(LocalDate fromDate, LocalDate toDate);

    List<Invoice> findByStatusInAndDueDateBeforeOrderByDueDateAscIdDesc(Collection<String> statuses, LocalDate dueDate);

    List<Invoice> findByStatusInOrderByDueDateAscIdDesc(Collection<String> statuses);
}
