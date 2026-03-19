package com.company.base.service;

import com.company.base.common.pagination.PageResponse;
import com.company.base.dto.request.host.RoomRequest;
import com.company.base.dto.request.host.roomManager.RoomManagerCreateReqDTO;
import com.company.base.dto.response.host.RoomResponse;
import com.company.base.dto.response.host.roomManager.RoomDetailResDTO;
import org.springframework.data.domain.Pageable;

public interface RoomManagementService {
    String createRoom(RoomManagerCreateReqDTO roomManagerCreateReqDTO);

    RoomResponse updateRoom(String id, RoomRequest request);

    RoomResponse getRoomById(String id);

    RoomDetailResDTO getRoomDetail(String id);

    PageResponse<RoomResponse> getRooms(String propertyId, Pageable pageable);

    void deleteRoom(String id);
}

