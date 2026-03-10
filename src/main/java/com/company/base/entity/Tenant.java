package com.company.base.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.Where;

import java.time.LocalDate;

@Entity
@Table(name = "tenants")
@Where(clause = "del_yn IS NULL OR del_yn <> 'Y'")
@Data
public class Tenant extends BaseEntity {
    // Unique tenant ID.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // Full name of tenant.
    private String fullName;
    // Contact phone number.
    private String phone;
    // Contact email.
    private String email;
    // National ID / citizen ID number.
    private String idCardNumber;
    // Portrait image reference (file metadata ID).
    private Long portraitImageId;
    // Temporary residence registration status with authorities.
    private Boolean temporaryResidenceDeclared;
    // Date of temporary residence declaration.
    private LocalDate temporaryResidenceDeclaredAt;
    // Hashed password for tenant account.
    private String passwordHash;
}
