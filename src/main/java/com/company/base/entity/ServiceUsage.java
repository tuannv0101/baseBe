package com.company.base.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "service_usages")
@Data
public class ServiceUsage extends BaseEntity {
    // ID duy nhất của bản ghi sử dụng dịch vụ.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // ID phòng sử dụng dịch vụ.
    private String roomId;
    // ID dịch vụ được sử dụng.
    private String serviceId;
    // Tháng ghi nhận chỉ số.
    private Integer month;
    // Năm ghi nhận chỉ số.
    private Integer year;
    // Chỉ số cũ.
    private Double oldValue;
    // Chỉ số mới.
    private Double newValue;
}
