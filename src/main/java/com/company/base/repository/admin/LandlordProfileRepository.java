package com.company.base.repository.admin;

import com.company.base.entity.LandlordProfile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for data access operations.
 */
public interface LandlordProfileRepository extends JpaRepository<LandlordProfile, String> {
    Optional<LandlordProfile> findByUserId(String userId);
    List<LandlordProfile> findByStatusIgnoreCaseOrderByCreatedAtDesc(String status);
    List<LandlordProfile> findAllByOrderByCreatedAtDesc();

    Page<LandlordProfile> findByStatusIgnoreCaseOrderByCreatedAtDesc(String status, Pageable pageable);
    Page<LandlordProfile> findAllByOrderByCreatedAtDesc(Pageable pageable);
}

