package com.company.base.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "maintenance_requests")
@Data
public class MaintenanceRequest extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String roomId;
    private String tenantId;
    private String title;
    private String description;
    private String priority;
    private String status;
    private String assignedTechnician;
    private LocalDateTime requestedAt;
    private LocalDateTime completedAt;
    private String note;
}
