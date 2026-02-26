package com.company.base.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

@Getter @Setter @MappedSuperclass @EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
    @CreatedDate @Column(updatable = false) private LocalDateTime createdAt;
    @LastModifiedDate private LocalDateTime updatedAt;
    @CreatedBy @Column(updatable = false) private String createdBy;
    @LastModifiedBy private String updatedBy;
}
