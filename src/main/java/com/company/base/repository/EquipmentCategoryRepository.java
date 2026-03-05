package com.company.base.repository;

import com.company.base.entity.EquipmentCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
/**
 * Repository for data access operations.
 */

public interface EquipmentCategoryRepository extends JpaRepository<EquipmentCategory, Long> {
    List<EquipmentCategory> findAllByOrderByNameAsc();
}
