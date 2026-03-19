package com.company.base.repository.admin;

import com.company.base.entity.LandlordSubscription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository for data access operations.
 */
public interface LandlordSubscriptionRepository extends JpaRepository<LandlordSubscription, String> {
    List<LandlordSubscription> findByLandlordProfileIdOrderByStartDateDescIdDesc(String landlordProfileId);
    List<LandlordSubscription> findByStartDateBetweenOrderByStartDateAscIdAsc(LocalDate fromDate, LocalDate toDate);

    Page<LandlordSubscription> findByLandlordProfileIdOrderByStartDateDescIdDesc(String landlordProfileId, Pageable pageable);
}

