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
 * Service quáº£n lÃ½ báº¥t Ä‘á»™ng sáº£n/tÃ²a nhÃ  vÃ  phÃ²ng.
 */
public interface PropertyManagementService {
    /**
     * Táº¡o má»›i tÃ²a nhÃ /báº¥t Ä‘á»™ng sáº£n.
     */
    PropertyResponse createProperty(PropertyRequest request);

    /**
     * Cáº­p nháº­t thÃ´ng tin tÃ²a nhÃ /báº¥t Ä‘á»™ng sáº£n theo ID.
     */
    PropertyResponse updateProperty(String id, PropertyRequest request);

    /**
     * Láº¥y chi tiáº¿t tÃ²a nhÃ /báº¥t Ä‘á»™ng sáº£n theo ID.
     */
    PropertyResponse getPropertyById(String id);

    /**
     * Láº¥y danh sÃ¡ch toÃ n bá»™ tÃ²a nhÃ /báº¥t Ä‘á»™ng sáº£n.
     */
    PageResponse<ListRoomResDTO> getAllProperties(PropertySearchRequest propertySearchRequest, Pageable pageable);

    List<PropertyResponse> getAllProperties();

    PageDTO<PropertyResponse> getAllProperties(Pageable pageable);

    /**
     * XÃ³a tÃ²a nhÃ /báº¥t Ä‘á»™ng sáº£n theo ID.
     */
    void deleteProperty(String id);

    /**
     * Láº¥y ma tráº­n phÃ²ng (táº§ng/phÃ²ng/tráº¡ng thÃ¡i) theo propertyId Ä‘á»ƒ hiá»ƒn thá»‹ dáº¡ng lÆ°á»›i.
     */
    PageResponse<RoomBasicInfoResponse> getRoomMatrix(String propertyId, Pageable pageable);
}


