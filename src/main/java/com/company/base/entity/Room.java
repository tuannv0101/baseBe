package com.company.base.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "rooms")
@Data
public class Room extends BaseEntity {
    // ID duy nhất của phòng.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // ID tòa nhà/bất động sản chứa phòng.
    private Long propertiesId;
    // Số/ký hiệu phòng.
    private String roomNumber;
    // Tầng của phòng.
    private Integer floor;
    // Giá thuê phòng.
    private String price;
    // Diện tích phòng (m2).
    private Float area;
    // Trạng thái phòng: AVAILABLE, OCCUPIED, MAINTENANCE.
    private String status; // AVAILABLE, OCCUPIED, MAINTENANCE
}
