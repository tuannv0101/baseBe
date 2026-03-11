package com.company.base.service;

import com.company.base.dto.request.host.PropertyRequest;
import com.company.base.dto.request.host.PropertySearchRequest;
import com.company.base.dto.response.host.PropertyResponse;
import com.company.base.dto.response.host.RoomMatrixResponse;

import com.company.base.common.pagination.PageResponse;
import com.company.base.dto.response.host.roomManager.ListRoomResDTO;
import org.springframework.data.domain.Pageable;

/**
 * Service quản lý bất động sản/tòa nhà và phòng.
 */
public interface PropertyManagementService {
    /**
     * Tạo mới tòa nhà/bất động sản.
     */
    PropertyResponse createProperty(PropertyRequest request);

    /**
     * Cập nhật thông tin tòa nhà/bất động sản theo ID.
     */
    PropertyResponse updateProperty(Long id, PropertyRequest request);

    /**
     * Lấy chi tiết tòa nhà/bất động sản theo ID.
     */
    PropertyResponse getPropertyById(Long id);

    /**
     * Lấy danh sách toàn bộ tòa nhà/bất động sản.
     */
    PageResponse<ListRoomResDTO> getAllProperties(PropertySearchRequest propertySearchRequest, Pageable pageable);

    /**
     * Xóa tòa nhà/bất động sản theo ID.
     */
    void deleteProperty(Long id);

    /**
     * Lấy ma trận phòng (tầng/phòng/trạng thái) theo propertyId để hiển thị dạng lưới.
     */
    PageResponse<RoomMatrixResponse> getRoomMatrix(Long propertyId, Pageable pageable);
}
