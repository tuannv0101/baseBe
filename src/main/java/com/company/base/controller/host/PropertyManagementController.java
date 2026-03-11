package com.company.base.controller.host;

import com.company.base.common.ApiResponse;
import com.company.base.dto.request.host.PropertyRequest;
import com.company.base.dto.request.host.PropertySearchRequest;
import com.company.base.dto.response.host.PropertyResponse;
import com.company.base.dto.response.host.RoomMatrixResponse;
import com.company.base.dto.response.host.roomManager.ListRoomResDTO;
import com.company.base.service.PropertyManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import com.company.base.common.pagination.PageResponse;

/**
 * REST controller that exposes API endpoints for this module.
 */

@RestController
@RequestMapping("/api/v1/host/property-management")
@RequiredArgsConstructor
public class PropertyManagementController {

    private final PropertyManagementService propertyManagementService;

    @PostMapping("/properties")
    public ApiResponse<PropertyResponse> createProperty(@RequestBody PropertyRequest request) {
        return ApiResponse.success(propertyManagementService.createProperty(request));
    }

    @PutMapping("/properties/{id}")
    public ApiResponse<PropertyResponse> updateProperty(@PathVariable Long id, @RequestBody PropertyRequest request) {
        return ApiResponse.success(propertyManagementService.updateProperty(id, request));
    }

    @GetMapping("/properties/{id}")
    public ApiResponse<PropertyResponse> getPropertyById(@PathVariable Long id) {
        return ApiResponse.success(propertyManagementService.getPropertyById(id));
    }

    @GetMapping("/properties")
    public ApiResponse<PageResponse<ListRoomResDTO>> getAllProperties(
            @ModelAttribute PropertySearchRequest propertySearchRequest,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        return ApiResponse.success(propertyManagementService.getAllProperties(propertySearchRequest, pageable));
    }

    @PutMapping("/properties/{id}/delete")
    public ApiResponse<Void> deleteProperty(@PathVariable Long id) {
        propertyManagementService.deleteProperty(id);
        return ApiResponse.success(null);
    }

    @GetMapping("/room-matrix")
    public ApiResponse<PageResponse<RoomMatrixResponse>> getRoomMatrix(
            @RequestParam(required = false) Long propertyId,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        return ApiResponse.success(propertyManagementService.getRoomMatrix(propertyId, pageable));
    }
}
