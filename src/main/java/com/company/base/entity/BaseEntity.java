package com.company.base.entity;

import com.company.base.common.util.SpringContextHolder;
import com.company.base.common.util.StringIdGenerator;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
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
    @Id
    @Column(length = 11, nullable = false, updatable = false)
    private String id;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @CreatedBy
    @Column(updatable = false)
    private String createdBy;

    @LastModifiedBy
    private String updatedBy;

    private String delYn;

    @PrePersist
    protected void prePersist() {
        if (id == null || id.isBlank()) {
            StringIdGenerator generator = SpringContextHolder.getBean(StringIdGenerator.class);
            id = generator.nextId(resolveTableName(), getIdPrefix());
        }
        if (delYn == null || delYn.isBlank()) {
            delYn = "N";
        }
    }

    protected abstract String getIdPrefix();

    private String resolveTableName() {
        Table table = this.getClass().getAnnotation(Table.class);
        if (table == null || table.name().isBlank()) {
            throw new IllegalStateException("Missing @Table(name=...) on " + this.getClass().getName());
        }
        return table.name();
    }
}
