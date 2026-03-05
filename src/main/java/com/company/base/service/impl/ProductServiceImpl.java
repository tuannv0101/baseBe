package com.company.base.service.impl;

import com.company.base.common.PageResponse;
import com.company.base.common.mapper.ProductMapper;
import com.company.base.dto.request.ProductRequest;
import com.company.base.dto.response.ProductResponse;
import com.company.base.entity.FileMetadata;
import com.company.base.entity.Product;
import com.company.base.repository.FileMetadataRepository;
import com.company.base.repository.ProductRepository;
import com.company.base.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
/**
 * Service implementation containing business logic for this module.
 */

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final FileMetadataRepository fileRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductResponse create(ProductRequest request) {
        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .stockQuantity(request.getStockQuantity())
                .build();

        if (request.getImageId() != null) {
            FileMetadata image = fileRepository.findById(request.getImageId()).orElse(null);
            product.setImage(image);
        }

        return productMapper.toDto(productRepository.save(product));
    }

    @Override
    public ProductResponse getById(Long id) {
        return productRepository.findById(id)
                .map(productMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @Override
    public List<ProductResponse> getAll() {
        return productMapper.toDto(productRepository.findAll());
    }

    @Override
    public PageResponse<ProductResponse> getAll(Pageable pageable) {
        Page<Product> productPage = productRepository.findAll(pageable);
        
        return PageResponse.<ProductResponse>builder()
                .content(productMapper.toDto(productPage.getContent()))
                .pageNumber(productPage.getNumber())
                .pageSize(productPage.getSize())
                .totalElements(productPage.getTotalElements())
                .totalPages(productPage.getTotalPages())
                .last(productPage.isLast())
                .build();
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}
