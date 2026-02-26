package com.company.base.common.mapper;

import com.company.base.dto.response.ProductResponse;
import com.company.base.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper extends EntityMapper<ProductResponse, Product> {

    @Override
    @Mapping(
            target = "imageUrl",
            expression = "java(entity.getImage() != null ? \"/api/v1/files/\" + entity.getImage().getId() : null)"
    )
    ProductResponse toDto(Product entity);
}
