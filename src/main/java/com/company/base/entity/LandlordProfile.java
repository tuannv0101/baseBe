package com.company.base.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "landlord_profiles")
@Data
public class LandlordProfile extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String businessName;
    private String contactPhone;
    // PENDING, APPROVED, LOCKED
    private String status;
    private String note;
}
