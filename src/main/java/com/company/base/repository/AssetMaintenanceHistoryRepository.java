package com.company.base.repository;

import com.company.base.entity.AssetMaintenanceHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
/**
 * Repository for data access operations.
 */

public interface AssetMaintenanceHistoryRepository extends JpaRepository<AssetMaintenanceHistory, Long> {
    List<AssetMaintenanceHistory> findByRoomAssetIdOrderByMaintenanceDateDescIdDesc(Long roomAssetId);
    List<AssetMaintenanceHistory> findByRoomAssetIdInOrderByMaintenanceDateDescIdDesc(Collection<Long> roomAssetIds);
}
