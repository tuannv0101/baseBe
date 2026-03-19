package com.company.base.dto.response.host;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response DTO carrying output data for API operations.
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OperationsDocumentResponse {
    private String id;

    private String title;

    private String documentType;

    private String fileId;

    private String fileUrl;

    private Boolean active;

    private String note;
}

