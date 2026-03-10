package com.company.base.service;

import com.company.base.dto.request.host.PropertyRequest;
import com.company.base.dto.response.host.PropertyResponse;
import com.company.base.dto.response.host.RoomMatrixResponse;

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
    PropertyResponse updateProperty(Long id, PropertyRequest request);

    /**
     * Lấy chi tiết tòa nhà/bất động sản theo ID.
     */
    PropertyResponse getPropertyById(Long id);

    /**
     * Lấy danh sách toàn bộ tòa nhà/bất động sản.
     */
    List<PropertyResponse> getAllProperties();

    /**
     * Xóa tòa nhà/bất động sản theo ID.
     */
    void deleteProperty(Long id);

    /**
     * Lấy ma trận phòng (tầng/phòng/trạng thái) theo propertyId để hiển thị dạng lưới.
     */
    List<RoomMatrixResponse> getRoomMatrix(Long propertyId);
}
