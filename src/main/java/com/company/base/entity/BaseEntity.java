package com.company.base.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
    // Thời điểm bản ghi được tạo.
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    // Thời điểm bản ghi được cập nhật gần nhất.
    @LastModifiedDate
    private LocalDateTime updatedAt;

    // Định danh người tạo bản ghi.
    @CreatedBy
    @Column(updatable = false)
    private String createdBy;

    // Định danh người cập nhật bản ghi gần nhất.
    @LastModifiedBy
    private String updatedBy;
}
