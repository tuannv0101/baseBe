package com.company.base.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "room_assets")
@Where(clause = "del_yn IS NULL OR del_yn <> 'Y'")
@Data
public class RoomAsset extends BaseEntity {
    // ID duy nhất của tài sản trong phòng.
// ID phòng sở hữu tài sản.
    private String roomId;
    // Tên danh mục thiết bị.
    private String name;
    // Thương hiệu thiết bị.
    private String brand;
    // Số serial của tài sản.
    private String serialNumber;
    // Trạng thái tài sản: NEW, GOOD, BROKEN, REPAIRING.
    private String status; // NEW, GOOD, BROKEN, REPAIRING

    @Override
    protected String getIdPrefix() {
        return "RSA";
    }
}


