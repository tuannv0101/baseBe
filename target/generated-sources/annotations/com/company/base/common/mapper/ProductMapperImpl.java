package com.company.base.common.mapper;

import com.company.base.dto.response.ProductResponse;
import com.company.base.entity.Product;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-26T08:03:37+0700",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.45.0.v20260128-0750, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public Product toEntity(ProductResponse dto) {
        if ( dto == null ) {
            return null;
        }

        Product.ProductBuilder product = Product.builder();

        product.description( dto.getDescription() );
        product.id( dto.getId() );
        product.name( dto.getName() );
        product.price( dto.getPrice() );
        product.stockQuantity( dto.getStockQuantity() );

        return product.build();
    }

    @Override
    public List<Product> toEntity(List<ProductResponse> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Product> list = new ArrayList<Product>( dtoList.size() );
        for ( ProductResponse productResponse : dtoList ) {
            list.add( toEntity( productResponse ) );
        }

        return list;
    }

    @Override
    public List<ProductResponse> toDto(List<Product> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<ProductResponse> list = new ArrayList<ProductResponse>( entityList.size() );
        for ( Product product : entityList ) {
            list.add( toDto( product ) );
        }

        return list;
    }

    @Override
    public ProductResponse toDto(Product entity) {
        if ( entity == null ) {
            return null;
        }

        ProductResponse.ProductResponseBuilder productResponse = ProductResponse.builder();

        productResponse.description( entity.getDescription() );
        productResponse.id( entity.getId() );
        productResponse.name( entity.getName() );
        productResponse.price( entity.getPrice() );
        productResponse.stockQuantity( entity.getStockQuantity() );

        productResponse.imageUrl( entity.getImage() != null ? "/api/v1/files/" + entity.getImage().getId() : null );

        return productResponse.build();
    }
}
