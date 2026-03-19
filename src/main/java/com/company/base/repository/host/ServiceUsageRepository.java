package com.company.base.repository.host;

import com.company.base.entity.ServiceUsage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for data access operations.
 */

public interface ServiceUsageRepository extends JpaRepository<ServiceUsage, String> {
    Optional<ServiceUsage> findByRoomIdAndServiceIdAndMonthAndYear(String roomId, String serviceId, Integer month, Integer year);

    List<ServiceUsage> findByMonthAndYearOrderByRoomIdAsc(Integer month, Integer year);

    List<ServiceUsage> findByServiceIdAndMonthAndYearOrderByRoomIdAsc(String serviceId, Integer month, Integer year);

    List<ServiceUsage> findByRoomIdAndMonthAndYearOrderByServiceIdAsc(String roomId, Integer month, Integer year);

    Page<ServiceUsage> findByMonthAndYearOrderByRoomIdAsc(Integer month, Integer year, Pageable pageable);

    Page<ServiceUsage> findByServiceIdAndMonthAndYearOrderByRoomIdAsc(String serviceId, Integer month, Integer year, Pageable pageable);
}

