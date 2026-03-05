package com.company.base.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity @Table(name = "products")
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class Product extends BaseEntity {
    // ID duy nhất của sản phẩm.
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Tên sản phẩm.
    @Column(nullable = false)
    private String name;

    // Mô tả chi tiết sản phẩm.
    private String description;

    // Giá bán sản phẩm.
    private BigDecimal price;

    // Số lượng tồn kho.
    private Integer stockQuantity;

    // Ảnh đại diện của sản phẩm.
    @OneToOne
    @JoinColumn(name = "image_id")
    private FileMetadata image;
}
