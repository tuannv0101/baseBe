package com.company.base.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "room_assets")
@Where(clause = "del_yn IS NULL OR del_yn <> 'Y'")
@Data
public class RoomAsset extends BaseEntity {
    // ID duy nhất của tài sản trong phòng.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // ID phòng sở hữu tài sản.
    private String roomId;
    // ID danh mục thiết bị.
    private String categoryId;
    // Số serial của tài sản.
    private String serialNumber;
    // Trạng thái tài sản: NEW, GOOD, BROKEN, REPAIRING.
    private String status; // NEW, GOOD, BROKEN, REPAIRING
}
