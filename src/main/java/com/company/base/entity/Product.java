package com.company.base.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity @Table(name = "products")
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class Product extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    private BigDecimal price;

    private Integer stockQuantity;

    @OneToOne
    @JoinColumn(name = "image_id")
    private FileMetadata image;
}
