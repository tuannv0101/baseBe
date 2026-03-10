package com.company.base.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Where;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "expense_records")
@Where(clause = "del_yn IS NULL OR del_yn <> 'Y'")
@Data
public class ExpenseRecord extends BaseEntity {
    // ID duy nhất của khoản chi.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Ngày phát sinh khoản chi.
    private LocalDate expenseDate;

    // Nhóm/hạng mục chi (ví dụ: sửa chữa, điện nước, vật tư...).
    private String category;

    // Số tiền chi.
    private BigDecimal amount;

    // Mô tả ngắn nội dung chi.
    private String description;

    // Nhà cung cấp/đơn vị nhận tiền (nếu có).
    private String vendor;

    // Người thanh toán/ghi nhận khoản chi (tên hoặc định danh).
    private String paidBy;

    // Ghi chú bổ sung.
    private String note;
}
