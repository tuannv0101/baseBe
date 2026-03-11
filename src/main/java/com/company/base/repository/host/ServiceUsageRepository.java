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

public interface ServiceUsageRepository extends JpaRepository<ServiceUsage, Long> {
    Optional<ServiceUsage> findByRoomIdAndServiceIdAndMonthAndYear(Long roomId, Long serviceId, Integer month, Integer year);

    List<ServiceUsage> findByMonthAndYearOrderByRoomIdAsc(Integer month, Integer year);

    List<ServiceUsage> findByServiceIdAndMonthAndYearOrderByRoomIdAsc(Long serviceId, Integer month, Integer year);

    List<ServiceUsage> findByRoomIdAndMonthAndYearOrderByServiceIdAsc(Long roomId, Integer month, Integer year);

    Page<ServiceUsage> findByMonthAndYearOrderByRoomIdAsc(Integer month, Integer year, Pageable pageable);

    Page<ServiceUsage> findByServiceIdAndMonthAndYearOrderByRoomIdAsc(Long serviceId, Integer month, Integer year, Pageable pageable);
}
