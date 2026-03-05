package com.company.base.service;

import com.company.base.dto.request.host.AssetMaintenanceHistoryRequest;
import com.company.base.dto.request.host.EquipmentCategoryRequest;
import com.company.base.dto.request.host.RoomAssetRequest;
import com.company.base.dto.response.host.AssetMaintenanceHistoryResponse;
import com.company.base.dto.response.host.EquipmentCategoryResponse;
import com.company.base.dto.response.host.RoomAssetResponse;

import java.util.List;

/**
 * Service contract defining operations for this module.
 */

public interface AssetManagementService {
    EquipmentCategoryResponse createCategory(EquipmentCategoryRequest request);

    EquipmentCategoryResponse updateCategory(Long id, EquipmentCategoryRequest request);

    EquipmentCategoryResponse getCategoryById(Long id);

    List<EquipmentCategoryResponse> getAllCategories();

    void deleteCategory(Long id);

    RoomAssetResponse createRoomAsset(RoomAssetRequest request);

    RoomAssetResponse updateRoomAsset(Long id, RoomAssetRequest request);

    RoomAssetResponse getRoomAssetById(Long id);

    List<RoomAssetResponse> getRoomAssetsByRoom(String roomId);

    void deleteRoomAsset(Long id);

    AssetMaintenanceHistoryResponse createMaintenanceHistory(Long roomAssetId, AssetMaintenanceHistoryRequest request);

    AssetMaintenanceHistoryResponse updateMaintenanceHistory(Long id, AssetMaintenanceHistoryRequest request);

    List<AssetMaintenanceHistoryResponse> getMaintenanceHistoryByRoomAsset(Long roomAssetId);

    List<AssetMaintenanceHistoryResponse> getMaintenanceHistoryByRoom(String roomId);
}
