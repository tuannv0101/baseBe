package com.company.base.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.Where;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "asset_maintenance_histories")
@Where(clause = "del_yn IS NULL OR del_yn <> 'Y'")
@Data
public class AssetMaintenanceHistory extends BaseEntity {
    // ID duy nhat cua lich su bao tri.
// ID tai san duoc bao tri.
    private String roomAssetId;

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

    @Override
    protected String getIdPrefix() {
        return "AMH";
    }
}


