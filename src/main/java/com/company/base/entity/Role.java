package com.company.base.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "roles")
@Where(clause = "del_yn IS NULL OR del_yn <> 'Y'")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role extends BaseEntity {
    // ID duy nhất của vai trò.
// Tên vai trò (ví dụ: ROLE_ADMIN, ROLE_USER).
    @Column(unique = true, nullable = false)
    private String name;

    // Mô tả vai trò.
    private String description;

    @Override
    protected String getIdPrefix() {
        return "ROL";
    }
}


