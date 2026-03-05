package com.company.base.repository;

import com.company.base.entity.Properties;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
/**
 * Repository for data access operations.
 */

public interface PropertiesRepository extends JpaRepository<Properties, Long> {
    List<Properties> findAllByOrderByNameAsc();
}
