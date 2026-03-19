package com.company.base.repository.admin;

import com.company.base.entity.SubscriptionPlan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for data access operations.
 */
public interface SubscriptionPlanRepository extends JpaRepository<SubscriptionPlan, String> {
    Optional<SubscriptionPlan> findByCodeIgnoreCase(String code);
    List<SubscriptionPlan> findAllByOrderByMonthlyPriceAsc();

    Page<SubscriptionPlan> findAllByOrderByMonthlyPriceAsc(Pageable pageable);
}

