package com.company.base.controller.host;

import com.company.base.common.ApiResponse;
import com.company.base.dto.request.host.AssetMaintenanceHistoryRequest;
import com.company.base.dto.request.host.EquipmentCategoryRequest;
import com.company.base.dto.request.host.RoomAssetRequest;
import com.company.base.dto.response.host.AssetMaintenanceHistoryResponse;
import com.company.base.dto.response.host.EquipmentCategoryResponse;
import com.company.base.dto.response.host.RoomAssetResponse;
import com.company.base.service.AssetManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.base.common.pagination.PageResponse;

/**
 * REST controller that exposes API endpoints for this module.
 */

@RestController
@RequestMapping("/api/v1/host/property-management/assets")
@RequiredArgsConstructor
public class AssetManagementController {

    private final AssetManagementService assetManagementService;

    @PostMapping("/categories")
    public ApiResponse<EquipmentCategoryResponse> createCategory(@RequestBody EquipmentCategoryRequest request) {
        return ApiResponse.success(assetManagementService.createCategory(request));
    }

    @PutMapping("/categories/{id}")
    public ApiResponse<EquipmentCategoryResponse> updateCategory(@PathVariable Long id, @RequestBody EquipmentCategoryRequest request) {
        return ApiResponse.success(assetManagementService.updateCategory(id, request));
    }

    @GetMapping("/categories/{id}")
    public ApiResponse<EquipmentCategoryResponse> getCategoryById(@PathVariable Long id) {
        return ApiResponse.success(assetManagementService.getCategoryById(id));
    }

    @GetMapping("/categories")
    public ApiResponse<PageResponse<EquipmentCategoryResponse>> getAllCategories(@PageableDefault(size = 20) Pageable pageable) {
        return ApiResponse.success(assetManagementService.getAllCategories(pageable));
    }

    @PutMapping("/categories/{id}/delete")
    public ApiResponse<Void> deleteCategory(@PathVariable Long id) {
        assetManagementService.deleteCategory(id);
        return ApiResponse.success(null);
    }

    @PostMapping("/room-assets")
    public ApiResponse<RoomAssetResponse> createRoomAsset(@RequestBody RoomAssetRequest request) {
        return ApiResponse.success(assetManagementService.createRoomAsset(request));
    }

    @PutMapping("/room-assets/{id}")
    public ApiResponse<RoomAssetResponse> updateRoomAsset(@PathVariable Long id, @RequestBody RoomAssetRequest request) {
        return ApiResponse.success(assetManagementService.updateRoomAsset(id, request));
    }

    @GetMapping("/room-assets/{id}")
    public ApiResponse<RoomAssetResponse> getRoomAssetById(@PathVariable Long id) {
        return ApiResponse.success(assetManagementService.getRoomAssetById(id));
    }

    @PutMapping("/room-assets/{id}/delete")
    public ApiResponse<Void> deleteRoomAsset(@PathVariable Long id) {
        assetManagementService.deleteRoomAsset(id);
        return ApiResponse.success(null);
    }

    @GetMapping("/rooms/{roomId}/inventory")
    public ApiResponse<PageResponse<RoomAssetResponse>> getRoomInventory(
            @PathVariable String roomId,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        return ApiResponse.success(assetManagementService.getRoomAssetsByRoom(roomId, pageable));
    }

    @PostMapping("/room-assets/{roomAssetId}/maintenance-history")
    public ApiResponse<AssetMaintenanceHistoryResponse> createMaintenanceHistory(
            @PathVariable Long roomAssetId,
            @RequestBody AssetMaintenanceHistoryRequest request
    ) {
        return ApiResponse.success(assetManagementService.createMaintenanceHistory(roomAssetId, request));
    }

    @PutMapping("/maintenance-history/{id}")
    public ApiResponse<AssetMaintenanceHistoryResponse> updateMaintenanceHistory(
            @PathVariable Long id,
            @RequestBody AssetMaintenanceHistoryRequest request
    ) {
        return ApiResponse.success(assetManagementService.updateMaintenanceHistory(id, request));
    }

    @GetMapping("/room-assets/{roomAssetId}/maintenance-history")
    public ApiResponse<PageResponse<AssetMaintenanceHistoryResponse>> getMaintenanceHistoryByRoomAsset(
            @PathVariable Long roomAssetId,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        return ApiResponse.success(assetManagementService.getMaintenanceHistoryByRoomAsset(roomAssetId, pageable));
    }

    @GetMapping("/rooms/{roomId}/maintenance-history")
    public ApiResponse<PageResponse<AssetMaintenanceHistoryResponse>> getMaintenanceHistoryByRoom(
            @PathVariable String roomId,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        return ApiResponse.success(assetManagementService.getMaintenanceHistoryByRoom(roomId, pageable));
    }
}
