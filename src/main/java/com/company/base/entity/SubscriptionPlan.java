package com.company.base.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "subscription_plans")
@Data
public class SubscriptionPlan extends BaseEntity {
    // ID duy nhất của gói đăng ký.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Mã gói (dùng cho nghiệp vụ/hiển thị, nên là duy nhất).
    private String code;

    // Tên gói.
    private String name;

    // Số lượng phòng tối đa được phép quản lý theo gói.
    private Integer maxRooms;

    // Giá theo tháng của gói.
    private BigDecimal monthlyPrice;

    // Cờ bật/tắt gói (đang cung cấp hay ngừng cung cấp).
    private Boolean active;

    // Mô tả chi tiết quyền lợi/nội dung gói.
    private String description;
}
