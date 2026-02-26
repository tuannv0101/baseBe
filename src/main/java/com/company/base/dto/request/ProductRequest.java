package com.company.base.dto.request;

import lombok.*;
import java.math.BigDecimal;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class ProductRequest {
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stockQuantity;
    private Long imageId;
}
