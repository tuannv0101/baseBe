package com.company.base.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.Where;

import java.math.BigDecimal;

@Entity
@Table(name = "services")
@Where(clause = "del_yn IS NULL OR del_yn <> 'Y'")
@Data
public class ServiceManager extends BaseEntity {
    // ID duy nhất của dịch vụ.
// Tên dịch vụ.
    private String name;

    private String propertyId;

    // Đơn giá dịch vụ.
    private BigDecimal unitPrice;

    // Đơn vị tính (kWh, m3, Person, Fixed).
    private String unitType; // kWh, m3, Person, Fixed

    @Override
    protected String getIdPrefix() {
        return "SER";
    }
}


