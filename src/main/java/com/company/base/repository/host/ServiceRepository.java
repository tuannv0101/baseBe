package com.company.base.repository.host;

import com.company.base.entity.ServiceManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository for data access operations.
 */

public interface ServiceRepository extends JpaRepository<ServiceManager, String> {
    List<ServiceManager> findAllByOrderByNameAsc();

    Page<ServiceManager> findAllByOrderByNameAsc(Pageable pageable);

    List<ServiceManager> findByPropertyIdOrderByNameAsc(String propertyId);

    Page<ServiceManager> findByPropertyIdOrderByNameAsc(String propertyId, Pageable pageable);
}

