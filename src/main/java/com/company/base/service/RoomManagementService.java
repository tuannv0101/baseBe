package com.company.base.service;

import com.company.base.common.pagination.PageResponse;
import com.company.base.dto.request.host.RoomRequest;
import com.company.base.dto.request.host.roomManager.RoomManagerCreateReqDTO;
import com.company.base.dto.response.host.RoomResponse;
import com.company.base.dto.response.host.roomManager.RoomDetailResDTO;
import org.springframework.data.domain.Pageable;

public interface RoomManagementService {
    Long createRoom(RoomManagerCreateReqDTO roomManagerCreateReqDTO);

    RoomResponse updateRoom(Long id, RoomRequest request);

    RoomResponse getRoomById(Long id);

    RoomDetailResDTO getRoomDetail(Long id);

    PageResponse<RoomResponse> getRooms(Long propertyId, Pageable pageable);

    void deleteRoom(Long id);
}
