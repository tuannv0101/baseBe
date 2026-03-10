package com.company.base.service;

import com.company.base.common.pagination.PageResponse;
import com.company.base.dto.request.host.RoomRequest;
import com.company.base.dto.response.host.RoomResponse;
import org.springframework.data.domain.Pageable;

public interface RoomManagementService {
    RoomResponse createRoom(RoomRequest request);

    RoomResponse updateRoom(Long id, RoomRequest request);

    RoomResponse getRoomById(Long id);

    PageResponse<RoomResponse> getRooms(Long propertyId, Pageable pageable);

    void deleteRoom(Long id);
}

