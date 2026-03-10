package com.company.base.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment_receipts")
@Data
public class PaymentReceipt extends BaseEntity {
    // ID duy nhất của phiếu thu.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ID hóa đơn liên quan.
    private Long invoiceId;

    // Mã phiếu thu nghiệp vụ.
    private String receiptCode;

    // ID phòng liên quan đến khoản thu.
    private String roomId;

    // Tên người nộp tiền.
    private String payerName;

    // Số tiền đã thu.
    private BigDecimal amount;

    // Phương thức thanh toán (ví dụ: CASH, TRANSFER, CARD...).
    private String paymentMethod;

    // Thời điểm thanh toán/ghi nhận thu.
    private LocalDateTime paymentTime;

    // Ghi chú bổ sung.
    private String note;
}
