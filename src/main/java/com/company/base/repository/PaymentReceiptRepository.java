package com.company.base.repository;

import com.company.base.entity.PaymentReceipt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
/**
 * Repository for data access operations.
 */

public interface PaymentReceiptRepository extends JpaRepository<PaymentReceipt, Long> {
    List<PaymentReceipt> findAllByOrderByPaymentTimeDescIdDesc();
    List<PaymentReceipt> findByPaymentTimeBetweenOrderByPaymentTimeAscIdAsc(LocalDateTime fromDateTime, LocalDateTime toDateTime);
}
