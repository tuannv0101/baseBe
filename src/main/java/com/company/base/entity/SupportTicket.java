package com.company.base.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "support_tickets")
@Data
public class SupportTicket extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long landlordProfileId;
    private String title;
    private String description;
    // OPEN, IN_PROGRESS, RESOLVED, CLOSED
    private String status;
    // LOW, MEDIUM, HIGH
    private String priority;
    private String assignedTo;
    private String resolutionNote;
}
