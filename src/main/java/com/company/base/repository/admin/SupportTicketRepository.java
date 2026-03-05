package com.company.base.repository.admin;

import com.company.base.entity.SupportTicket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository for data access operations.
 */
public interface SupportTicketRepository extends JpaRepository<SupportTicket, Long> {
    List<SupportTicket> findAllByOrderByCreatedAtDesc();
    List<SupportTicket> findByStatusIgnoreCaseOrderByCreatedAtDesc(String status);
}
