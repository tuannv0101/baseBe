package com.company.base.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "equipment_categories")
@Where(clause = "del_yn IS NULL OR del_yn <> 'Y'")
@Data
public class EquipmentCategory extends BaseEntity {
    // ID duy nhất của danh mục thiết bị.
// Tên danh mục thiết bị.
    private String name;

    // Thương hiệu thiết bị.
    private String brand;

    @Override
    protected String getIdPrefix() {
        return "EQC";
    }
}


