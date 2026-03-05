package com.company.base.repository.admin;

import com.company.base.entity.SubscriptionPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for data access operations.
 */
public interface SubscriptionPlanRepository extends JpaRepository<SubscriptionPlan, Long> {
    Optional<SubscriptionPlan> findByCodeIgnoreCase(String code);
    List<SubscriptionPlan> findAllByOrderByMonthlyPriceAsc();
}
