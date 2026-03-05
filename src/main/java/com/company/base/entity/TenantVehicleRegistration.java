package com.company.base.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "tenant_vehicle_registrations")
@Data
public class TenantVehicleRegistration extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tenantId;
    private String roomId;
    // MOTORBIKE, CAR...
    private String vehicleType;
    private String plateNumber;
    private LocalDate registeredDate;
    // PENDING, APPROVED, REJECTED
    private String status;
    private String note;
}
