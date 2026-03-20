package com.company.base.service;

import com.company.base.common.pagination.PageDTO;
import com.company.base.common.pagination.PageResponse;
import com.company.base.dto.request.host.PropertyRequest;
import com.company.base.dto.request.host.PropertySearchRequest;
import com.company.base.dto.response.host.PropertyResponse;
import com.company.base.dto.response.host.RoomBasicInfoResponse;
import com.company.base.dto.response.host.roomManager.ListRoomResDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

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
    PropertyResponse updateProperty(String id, PropertyRequest request);

    /**
     * Lấy chi tiết tòa nhà/bất động sản theo ID.
     */
    PropertyResponse getPropertyById(String id);

    /**
     * Lấy danh sách toàn bộ tòa nhà/bất động sản.
     */
    PageResponse<ListRoomResDTO> getAllProperties(PropertySearchRequest propertySearchRequest, Pageable pageable);


    PageDTO<PropertyResponse> getAllProperties(Pageable pageable);

    /**
     * Xóa tòa nhà/bất động sản theo ID.
     */
    void deleteProperty(String id);

    /**
     * Lấy ma trận phòng (tầng/phòng/trạng thái) theo propertyId để hiển thị dạng lưới.
     */
    PageResponse<RoomBasicInfoResponse> getRoomMatrix(String propertyId, Pageable pageable);
}


