package com.company.base.service.impl;

import com.company.base.common.pagination.PageResponse;
import com.company.base.dto.request.host.RoomRequest;
import com.company.base.dto.response.host.RoomResponse;
import com.company.base.entity.Properties;
import com.company.base.entity.Room;
import com.company.base.exception.AppException;
import com.company.base.repository.host.PropertiesRepository;
import com.company.base.repository.host.RoomManagementRepository;
import com.company.base.service.RoomManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomManagementServiceImpl implements RoomManagementService {
    private final RoomManagementRepository roomManagementRepository;
    private final PropertiesRepository propertiesRepository;

    @Override
    public RoomResponse createRoom(RoomRequest request) {
        if (request.getPropertyId() == null || request.getPropertyId().isBlank()) {
            throw new AppException(HttpStatus.BAD_REQUEST.value(), "propertyId is required");
        }
        Room entity = new Room();
        applyUpdate(entity, request);
        return toResponse(roomManagementRepository.save(entity));
    }

    @Override
    public RoomResponse updateRoom(Long id, RoomRequest request) {
        Room entity = getRoomEntity(id);
        applyUpdate(entity, request);
        return toResponse(roomManagementRepository.save(entity));
    }

    @Override
    public RoomResponse getRoomById(Long id) {
        return toResponse(getRoomEntity(id));
    }

    @Override
    public PageResponse<RoomResponse> getRooms(Long propertyId, Pageable pageable) {
        Page<RoomResponse> page = (propertyId == null)
                ? roomManagementRepository.findAllByOrderByIdDesc(pageable).map(this::toResponse)
                : roomManagementRepository.findByPropertiesIdOrderByFloorAscRoomNumberAsc(propertyId, pageable).map(this::toResponse);
        return PageResponse.of(page);
    }

    @Override
    public void deleteRoom(Long id) {
        Room entity = getRoomEntity(id);
        entity.setDelYn("Y");
        roomManagementRepository.save(entity);
    }

    private void applyUpdate(Room entity, RoomRequest request) {
        if (request.getPropertyId() != null) {
            Long propertyId = parseLong(request.getPropertyId(), "propertyId");
            Properties property = propertiesRepository.findById(propertyId)
                    .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND.value(), "Property not found"));
            entity.setPropertiesId(property.getId());
        }
        entity.setArea(request.getArea());
        entity.setFloor(request.getFloor());
        entity.setPrice(request.getPrice());
        entity.setRoomNumber(request.getRoomNumber());
        entity.setStatus(normalize(request.getStatus()));
    }

    private String normalize(String value) {
        return value == null ? null : value.trim().toUpperCase();
    }

    private Long parseLong(String value, String field) {
        try {
            return Long.valueOf(value);
        } catch (Exception e) {
            throw new AppException(HttpStatus.BAD_REQUEST.value(), "Invalid " + field);
        }
    }

    private Room getRoomEntity(Long id) {
        return roomManagementRepository.findById(id)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND.value(), "Room not found"));
    }

    private RoomResponse toResponse(Room entity) {
        return RoomResponse.builder()
                .roomId(entity.getId())
                .propertyId(entity.getPropertiesId() != null ? String.valueOf(entity.getPropertiesId()) : null)
                .area(entity.getArea())
                .floor(entity.getFloor())
                .price(entity.getPrice())
                .roomNumber(entity.getRoomNumber())
                .status(entity.getStatus())
                .build();
    }
}
