package com.company.base.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.Where;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "invoices")
@Where(clause = "del_yn IS NULL OR del_yn <> 'Y'")
@Data
public class InvoiceManager extends BaseEntity {
    // ID duy nhất của hóa đơn.
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

    @Override
    protected String getIdPrefix() {
        return "INV";
    }
}


