package com.company.base.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "invoices")
@Data
public class Invoice extends BaseEntity {
    // ID duy nhất của hóa đơn.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ID hợp đồng liên quan.
    private String contractId;

    // Mã hóa đơn nghiệp vụ.
    private String invoiceCode;

    // Tổng số tiền phải thanh toán.
    private BigDecimal totalAmount;

    // Trạng thái hóa đơn: UNPAID, PAID, OVERDUE.
    private String status; // UNPAID, PAID, OVERDUE

    // Hạn thanh toán.
    private LocalDate dueDate;

    // Thời điểm thanh toán thực tế.
    private LocalDateTime paymentDate;
}
