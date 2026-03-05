package com.company.base.repository;

import com.company.base.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * Repository for data access operations.
 */

public interface ProductRepository extends JpaRepository<Product, Long> {
}
