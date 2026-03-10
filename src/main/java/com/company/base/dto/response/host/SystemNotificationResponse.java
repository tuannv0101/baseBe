package com.company.base.dto.response.host;

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
public class SystemNotificationResponse {
    private String type;

    private String severity;

    private String title;

    private String message;

    private LocalDateTime createdAt;

    private String referenceType;

    private String referenceId;
}
