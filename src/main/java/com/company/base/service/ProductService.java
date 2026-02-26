package com.company.base.service;

import com.company.base.common.PageResponse;
import com.company.base.dto.request.ProductRequest;
import com.company.base.dto.response.ProductResponse;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface ProductService {
    ProductResponse create(ProductRequest request);
    ProductResponse getById(Long id);
    List<ProductResponse> getAll();
    PageResponse<ProductResponse> getAll(Pageable pageable);
    void delete(Long id);
}
