package com.company.base.repository;

import com.company.base.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
/**
 * Repository for data access operations.
 */

public interface ServiceRepository extends JpaRepository<Service, Long> {
    List<Service> findAllByOrderByNameAsc();
}
