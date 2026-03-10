package com.company.base.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "equipment_categories")
@Data
public class EquipmentCategory extends BaseEntity {
    // ID duy nhất của danh mục thiết bị.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Tên danh mục thiết bị.
    private String name;

    // Thương hiệu thiết bị.
    private String brand;
}
