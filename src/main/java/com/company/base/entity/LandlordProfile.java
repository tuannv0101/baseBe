package com.company.base.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "landlord_profiles")
@Where(clause = "del_yn IS NULL OR del_yn <> 'Y'")
@Data
public class LandlordProfile extends BaseEntity {
    // ID duy nhất của hồ sơ chủ nhà.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ID tài khoản người dùng liên kết (bảng users).
    private Long userId;

    // Tên cá nhân/doanh nghiệp cho mục đích hiển thị/xuất hóa đơn (nếu có).
    private String businessName;

    // Số điện thoại liên hệ chính.
    private String contactPhone;

    // Trạng thái hồ sơ chủ nhà: PENDING, APPROVED, LOCKED.
    private String status;

    // Ghi chú nội bộ.
    private String note;
}
