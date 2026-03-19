package com.company.base.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "system_configs")
@Where(clause = "del_yn IS NULL OR del_yn <> 'Y'")
@Data
public class SystemConfig extends BaseEntity {
    // ID duy nhất của cấu hình hệ thống.
// Khóa cấu hình (duy nhất) để tra cứu theo nghiệp vụ.
    @Column(unique = true, nullable = false)
    private String configKey;

    // Giá trị cấu hình (TEXT để lưu chuỗi dài/JSON nếu cần).
    @Column(columnDefinition = "TEXT")
    private String configValue;

    // Mô tả ý nghĩa cấu hình.
    private String description;

    @Override
    protected String getIdPrefix() {
        return "SYS";
    }
}


