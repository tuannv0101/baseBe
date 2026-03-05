package com.company.base.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "properties")
@Data
public class Properties extends BaseEntity {
    // ID duy nhất của tòa nhà/bất động sản.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // Tên tòa nhà/bất động sản.
    private String name;
    // Địa chỉ tòa nhà/bất động sản.
    private String address;
    // Tổng số tầng.
    private Integer totalFloors;
}
