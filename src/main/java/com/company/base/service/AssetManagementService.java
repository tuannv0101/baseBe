package com.company.base.service;

import com.company.base.dto.request.host.AssetMaintenanceHistoryRequest;
import com.company.base.dto.request.host.EquipmentCategoryRequest;
import com.company.base.dto.request.host.RoomAssetRequest;
import com.company.base.dto.response.host.AssetMaintenanceHistoryResponse;
import com.company.base.dto.response.host.EquipmentCategoryResponse;
import com.company.base.dto.response.host.RoomAssetResponse;

import com.company.base.common.pagination.PageResponse;
import org.springframework.data.domain.Pageable;

/**
 * Service quản lý tài sản/phòng và lịch sử bảo trì.
 */
public interface AssetManagementService {
    /**
     * Tạo mới danh mục thiết bị (ví dụ: Điều hòa, Bình nóng lạnh...).
     */
    EquipmentCategoryResponse createCategory(EquipmentCategoryRequest request);

    /**
     * Cập nhật thông tin danh mục thiết bị theo ID.
     */
    EquipmentCategoryResponse updateCategory(Long id, EquipmentCategoryRequest request);

    /**
     * Lấy chi tiết danh mục thiết bị theo ID.
     */
    EquipmentCategoryResponse getCategoryById(Long id);

    /**
     * Lấy danh sách toàn bộ danh mục thiết bị.
     */
    PageResponse<EquipmentCategoryResponse> getAllCategories(Pageable pageable);

    /**
     * Xóa danh mục thiết bị theo ID.
     */
    void deleteCategory(Long id);

    /**
     * Tạo mới tài sản gắn với một phòng (room asset).
     */
    RoomAssetResponse createRoomAsset(RoomAssetRequest request);

    /**
     * Cập nhật thông tin tài sản trong phòng theo ID.
     */
    RoomAssetResponse updateRoomAsset(Long id, RoomAssetRequest request);

    /**
     * Lấy chi tiết tài sản trong phòng theo ID.
     */
    RoomAssetResponse getRoomAssetById(Long id);

    /**
     * Lấy danh sách tài sản theo phòng.
     */
    PageResponse<RoomAssetResponse> getRoomAssetsByRoom(Long roomId, Pageable pageable);

    /**
     * Xóa tài sản trong phòng theo ID.
     */
    void deleteRoomAsset(Long id);

    /**
     * Tạo lịch sử bảo trì cho một tài sản trong phòng.
     */
    AssetMaintenanceHistoryResponse createMaintenanceHistory(Long roomAssetId, AssetMaintenanceHistoryRequest request);

    /**
     * Cập nhật lịch sử bảo trì theo ID bản ghi.
     */
    AssetMaintenanceHistoryResponse updateMaintenanceHistory(Long id, AssetMaintenanceHistoryRequest request);

    /**
     * Lấy lịch sử bảo trì theo ID tài sản trong phòng.
     */
    PageResponse<AssetMaintenanceHistoryResponse> getMaintenanceHistoryByRoomAsset(Long roomAssetId, Pageable pageable);

    /**
     * Lấy lịch sử bảo trì theo phòng (tổng hợp từ các tài sản trong phòng).
     */
    PageResponse<AssetMaintenanceHistoryResponse> getMaintenanceHistoryByRoom(Long roomId, Pageable pageable);
}
