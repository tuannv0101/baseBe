package com.company.base.controller.host;

import com.company.base.common.ApiResponse;
import com.company.base.common.pagination.PageResponse;
import com.company.base.dto.request.host.RoomRequest;
import com.company.base.dto.response.host.RoomResponse;
import com.company.base.service.RoomManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/host/property-management/rooms")
@RequiredArgsConstructor
public class RoomManagementController {
    private final RoomManagementService roomManagementService;

    @PostMapping
    public ApiResponse<RoomResponse> createRoom(@RequestBody RoomRequest request) {
        return ApiResponse.success(roomManagementService.createRoom(request));
    }

    @PutMapping("/{id}")
    public ApiResponse<RoomResponse> updateRoom(@PathVariable Long id, @RequestBody RoomRequest request) {
        return ApiResponse.success(roomManagementService.updateRoom(id, request));
    }

    @GetMapping("/{id}")
    public ApiResponse<RoomResponse> getRoomById(@PathVariable Long id) {
        return ApiResponse.success(roomManagementService.getRoomById(id));
    }

    @GetMapping
    public ApiResponse<PageResponse<RoomResponse>> getRooms(
            @RequestParam(required = false) Long propertyId,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        return ApiResponse.success(roomManagementService.getRooms(propertyId, pageable));
    }

    @PutMapping("/{id}/delete")
    public ApiResponse<Void> deleteRoom(@PathVariable Long id) {
        roomManagementService.deleteRoom(id);
        return ApiResponse.success(null);
    }
}

