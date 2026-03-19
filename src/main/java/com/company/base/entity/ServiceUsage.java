package com.company.base.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "service_usages")
@Where(clause = "del_yn IS NULL OR del_yn <> 'Y'")
@Data
public class ServiceUsage extends BaseEntity {
    // ID duy nhất của bản ghi sử dụng dịch vụ.
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

    @Override
    protected String getIdPrefix() {
        return "SUS";
    }
}


