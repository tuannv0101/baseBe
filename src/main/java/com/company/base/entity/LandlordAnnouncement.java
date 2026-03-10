package com.company.base.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "landlord_announcements")
@Data
public class LandlordAnnouncement extends BaseEntity {
    // ID duy nhất của thông báo.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ID hồ sơ chủ nhà đăng thông báo.
    private Long landlordProfileId;

    // ID phòng liên quan (nếu thông báo gắn với một phòng cụ thể).
    private String roomId;

    // Tiêu đề thông báo.
    private String title;

    // Nội dung chi tiết thông báo.
    private String content;

    // Cờ bật/tắt hiển thị thông báo.
    private Boolean active;
}
