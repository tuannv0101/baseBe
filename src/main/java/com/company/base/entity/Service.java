package com.company.base.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "services")
@Data
public class Service extends BaseEntity {
    // ID duy nhất của dịch vụ.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // Tên dịch vụ.
    private String name;
    // Đơn giá dịch vụ.
    private BigDecimal unitPrice;
    // Đơn vị tính (kWh, m3, Person, Fixed).
    private String unitType; // kWh, m3, Person, Fixed
}
