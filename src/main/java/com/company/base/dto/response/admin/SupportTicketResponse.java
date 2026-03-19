package com.company.base.dto.response.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Response DTO carrying output data for API operations.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SupportTicketResponse {
    private String id;

    private String landlordProfileId;

    private String title;

    private String description;

    private String status;

    private String priority;

    private String assignedTo;

    private String resolutionNote;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}

