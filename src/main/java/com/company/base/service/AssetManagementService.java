package com.company.base.service;

import com.company.base.dto.request.AssetMaintenanceHistoryRequest;
import com.company.base.dto.request.EquipmentCategoryRequest;
import com.company.base.dto.request.RoomAssetRequest;
import com.company.base.dto.response.AssetMaintenanceHistoryResponse;
import com.company.base.dto.response.EquipmentCategoryResponse;
import com.company.base.dto.response.RoomAssetResponse;

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
