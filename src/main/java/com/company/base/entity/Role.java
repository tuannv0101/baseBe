package com.company.base.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "roles")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    // ID duy nhất của vai trò.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Tên vai trò (ví dụ: ROLE_ADMIN, ROLE_USER).
    @Column(unique = true, nullable = false)
    private String name;

    // Mô tả vai trò.
    private String description;
}
