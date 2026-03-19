package com.company.base.repository.host;

import com.company.base.entity.PaymentReceipt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository for data access operations.
 */

public interface PaymentReceiptRepository extends JpaRepository<PaymentReceipt, String> {
    List<PaymentReceipt> findAllByOrderByPaymentTimeDescIdDesc();

    List<PaymentReceipt> findByPaymentTimeBetweenOrderByPaymentTimeAscIdAsc(LocalDateTime fromDateTime, LocalDateTime toDateTime);

    List<PaymentReceipt> findByRoomIdOrderByPaymentTimeDescIdDesc(String roomId);

    List<PaymentReceipt> findByInvoiceIdOrderByPaymentTimeDescIdDesc(String invoiceId);

    Page<PaymentReceipt> findAllByOrderByPaymentTimeDescIdDesc(Pageable pageable);
}

