package com.company.base.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "asset_maintenance_histories")
@Data
public class AssetMaintenanceHistory extends BaseEntity {
    // ID duy nhat cua lich su bao tri.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // ID tai san duoc bao tri.
    private Long roomAssetId;
    // Ngay thuc hien bao tri.
    private LocalDate maintenanceDate;
    // Loai bao tri (PREVENTIVE/CORRECTIVE).
    private String maintenanceType;
    // Noi dung bao tri.
    private String description;
    // Don vi/nguoi thuc hien bao tri.
    private String vendor;
    // Chi phi bao tri.
    private BigDecimal cost;
    // Trang thai bao tri (PENDING/IN_PROGRESS/COMPLETED).
    private String status;
    // Ghi chu bo sung.
    private String note;
}
