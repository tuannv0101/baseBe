package com.company.base.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "landlord_subscriptions")
@Data
public class LandlordSubscription extends BaseEntity {
    // ID duy nhất của bản ghi đăng ký gói.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ID hồ sơ chủ nhà đăng ký.
    private Long landlordProfileId;

    // ID gói đăng ký (subscription_plans).
    private Long planId;

    // Ngày bắt đầu hiệu lực gói.
    private LocalDate startDate;

    // Ngày kết thúc hiệu lực gói.
    private LocalDate endDate;

    // Trạng thái đăng ký: ACTIVE, EXPIRED, CANCELLED.
    private String status;

    // Số tiền đã thanh toán cho gói.
    private BigDecimal amountPaid;
}
