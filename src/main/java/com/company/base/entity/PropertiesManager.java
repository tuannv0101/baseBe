package com.company.base.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "properties")
@Where(clause = "del_yn IS NULL OR del_yn <> 'Y'")
@Data
public class PropertiesManager extends BaseEntity {
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
    // Trạng thái tòa nhà
    private String status;
}
