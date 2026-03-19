package com.company.base.repository.host;

import com.company.base.entity.AssetMaintenanceHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

/**
 * Repository for data access operations.
 */

public interface AssetMaintenanceHistoryRepository extends JpaRepository<AssetMaintenanceHistory, String> {
    List<AssetMaintenanceHistory> findByRoomAssetIdOrderByMaintenanceDateDescIdDesc(String roomAssetId);

    List<AssetMaintenanceHistory> findByRoomAssetIdInOrderByMaintenanceDateDescIdDesc(Collection<String> roomAssetIds);

    Page<AssetMaintenanceHistory> findByRoomAssetIdOrderByMaintenanceDateDescIdDesc(String roomAssetId, Pageable pageable);

    Page<AssetMaintenanceHistory> findByRoomAssetIdInOrderByMaintenanceDateDescIdDesc(Collection<String> roomAssetIds, Pageable pageable);
}

