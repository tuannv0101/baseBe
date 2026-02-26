package com.company.base.dto.response;

import lombok.*;
import java.math.BigDecimal;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stockQuantity;
    private String imageUrl;
}
