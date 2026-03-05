package com.company.base.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "landlord_subscriptions")
@Data
public class LandlordSubscription extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long landlordProfileId;
    private Long planId;
    private LocalDate startDate;
    private LocalDate endDate;
    // ACTIVE, EXPIRED, CANCELLED
    private String status;
    private BigDecimal amountPaid;
}
