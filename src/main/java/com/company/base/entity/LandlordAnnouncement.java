package com.company.base.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "landlord_announcements")
@Where(clause = "del_yn IS NULL OR del_yn <> 'Y'")
@Data
public class LandlordAnnouncement extends BaseEntity {
    // ID duy nhất của thông báo.
// ID hồ sơ chủ nhà đăng thông báo.
    private String landlordProfileId;

    // ID phòng liên quan (nếu thông báo gắn với một phòng cụ thể).
    private String roomId;

    // Tiêu đề thông báo.
    private String title;

    // Nội dung chi tiết thông báo.
    private String content;

    // Cờ bật/tắt hiển thị thông báo.
    private Boolean active;

    @Override
    protected String getIdPrefix() {
        return "LAN";
    }
}


