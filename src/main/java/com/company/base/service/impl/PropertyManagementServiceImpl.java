package com.company.base.service.impl;

import com.company.base.common.pagination.PageResponse;
import com.company.base.dto.request.host.PropertyRequest;
import com.company.base.dto.request.host.PropertySearchRequest;
import com.company.base.dto.response.host.PropertyResponse;
import com.company.base.dto.response.host.RoomBasicInfoResponse;
import com.company.base.dto.response.host.roomManager.ListRoomResDTO;
import com.company.base.dto.response.host.roomManager.ListRoomResProjection;
import com.company.base.entity.PropertiesManager;
import com.company.base.entity.RoomManager;
import com.company.base.exception.AppException;
import com.company.base.repository.host.PropertiesRepository;
import com.company.base.repository.host.RoomManagementRepository;
import com.company.base.service.PropertyManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service implementation containing business logic for this module.
 */

@Service
@RequiredArgsConstructor
public class PropertyManagementServiceImpl implements PropertyManagementService {

    private final PropertiesRepository propertiesRepository;
    private final RoomManagementRepository roomManagementRepository;

    @Override
    public PropertyResponse createProperty(PropertyRequest request) {
        PropertiesManager entity = new PropertiesManager();
        entity.setName(request.getName());
        entity.setAddress(request.getAddress());
        entity.setTotalFloors(request.getTotalFloors());
        return toPropertyResponse(propertiesRepository.save(entity));
    }

    @Override
    public PropertyResponse updateProperty(Long id, PropertyRequest request) {
        PropertiesManager entity = getPropertyEntity(id);
        entity.setName(request.getName());
        entity.setAddress(request.getAddress());
        entity.setTotalFloors(request.getTotalFloors());
        return toPropertyResponse(propertiesRepository.save(entity));
    }

    @Override
    public PropertyResponse getPropertyById(Long id) {
        return toPropertyResponse(getPropertyEntity(id));
    }

    @Override
    public PageResponse<ListRoomResDTO> getAllProperties(PropertySearchRequest propertySearchRequest, Pageable pageable) {
        String nameProperty = propertySearchRequest != null ? propertySearchRequest.getNameProperty() : null;
        String statusRoom = propertySearchRequest != null ? propertySearchRequest.getStatusRoom() : null;
        String typeRoom = propertySearchRequest != null ? propertySearchRequest.getTypeRoom() : null;
        String tenantName = propertySearchRequest != null ? propertySearchRequest.getTenantName() : null;
        String priceRoom = propertySearchRequest != null ? propertySearchRequest.getPriceRoom() : null;

        Page<ListRoomResDTO> dtoPage = propertiesRepository
                .searchProperties(nameProperty, statusRoom, typeRoom, tenantName, priceRoom, pageable)
                .map(this::toListRoomResDTO);
        return PageResponse.of(dtoPage);
    }

    @Override
    public List<PropertyResponse> getAllProperties() {
        return propertiesRepository.findAllByOrderByNameAsc()
                .stream()
                .map(this::toPropertyResponse)
                .toList();
    }

    private ListRoomResDTO toListRoomResDTO(ListRoomResProjection p) {
        return ListRoomResDTO.builder()
                .id(p.getId())
                .roomNumber(p.getRoomNumber())
                .propertyName(p.getPropertyName())
                .typeRoom(p.getTypeRoom())
                .price(p.getPrice())
                .tenantName(p.getTenantName())
                .statusRoom(p.getStatusRoom())
                .build();
    }

    @Override
    public void deleteProperty(Long id) {
        PropertiesManager entity = getPropertyEntity(id);
        entity.setDelYn("Y");
        propertiesRepository.save(entity);
    }

    @Override
    public PageResponse<RoomBasicInfoResponse> getRoomMatrix(Long propertyId, Pageable pageable) {
        Page<RoomBasicInfoResponse> dtoPage;
        if (propertyId != null) {
            dtoPage = roomManagementRepository.findByPropertiesIdOrderByFloorAscRoomNumberAsc(propertyId, pageable)
                    .map(this::toRoomBasicInfoResponse);
        } else {
            dtoPage = roomManagementRepository.findAllByOrderByIdDesc(pageable)
                    .map(this::toRoomBasicInfoResponse);
        }
        return PageResponse.of(dtoPage);
    }

    private RoomBasicInfoResponse toRoomBasicInfoResponse(RoomManager roomManager) {
        return RoomBasicInfoResponse.builder()
                .roomId(roomManager.getId())
                .propertyId(roomManager.getPropertiesId())
                .floor(roomManager.getFloor())
                .roomNumber(roomManager.getRoomNumber())
                .status(roomManager.getStatus())
                .price(roomManager.getPrice())
                .area(roomManager.getArea())
                .build();
    }

    private PropertyResponse toPropertyResponse(PropertiesManager entity) {
        return PropertyResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .address(entity.getAddress())
                .totalFloors(entity.getTotalFloors())
                .build();
    }

    private PropertiesManager getPropertyEntity(Long id) {
        return propertiesRepository.findById(id)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND.value(), "Property not found"));
    }
}
