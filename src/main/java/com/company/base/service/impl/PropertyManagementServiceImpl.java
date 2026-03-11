package com.company.base.service.impl;

import com.company.base.common.pagination.PageResponse;
import com.company.base.common.pagination.PaginationUtils;
import com.company.base.dto.request.host.PropertyRequest;
import com.company.base.dto.request.host.PropertySearchRequest;
import com.company.base.dto.response.host.PropertyResponse;
import com.company.base.dto.response.host.RoomMatrixResponse;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
    public PageResponse<RoomMatrixResponse> getRoomMatrix(Long propertyId, Pageable pageable) {
        if (propertyId != null) {
            List<RoomMatrixResponse> one = List.of(buildRoomMatrix(
                    getPropertyEntity(propertyId),
                    roomManagementRepository.findByPropertiesIdOrderByFloorAscRoomNumberAsc(propertyId)
            ));
            return PaginationUtils.paginateList(one, pageable);
        }

        Page<PropertiesManager> propsPage = propertiesRepository.findAllByOrderByNameAsc(pageable);
        List<RoomMatrixResponse> matrices = propsPage.getContent().stream()
                .map(p -> buildRoomMatrix(p, roomManagementRepository.findByPropertiesIdOrderByFloorAscRoomNumberAsc(p.getId())))
                .toList();
        Page<RoomMatrixResponse> dtoPage = new PageImpl<>(matrices, pageable, propsPage.getTotalElements());
        return PageResponse.of(dtoPage);
    }

    private RoomMatrixResponse buildRoomMatrix(PropertiesManager property, List<RoomManager> roomManagers) {
        Map<Integer, List<RoomManager>> groupedByFloor = roomManagers.stream()
                .collect(Collectors.groupingBy(RoomManager::getFloor, TreeMap::new, Collectors.toList()));

        List<RoomMatrixResponse.FloorRow> floors = groupedByFloor.entrySet()
                .stream()
                .sorted(Comparator.comparingInt(Map.Entry::getKey))
                .map(entry -> RoomMatrixResponse.FloorRow.builder()
                        .floor(entry.getKey())
                        .rooms(entry.getValue().stream().map(this::toRoomCell).toList())
                        .build())
                .toList();

        int total = roomManagers.size();
        int available = countByStatus(roomManagers, "AVAILABLE");
        int occupied = countByStatus(roomManagers, "OCCUPIED");
        int maintenance = countByStatus(roomManagers, "MAINTENANCE");

        return RoomMatrixResponse.builder()
                .propertyId(property.getId())
                .propertyName(property.getName())
                .address(property.getAddress())
                .floors(floors)
                .summary(RoomMatrixResponse.Summary.builder()
                        .totalRooms(total)
                        .availableRooms(available)
                        .occupiedRooms(occupied)
                        .maintenanceRooms(maintenance)
                        .build())
                .build();
    }

    private int countByStatus(List<RoomManager> roomManagers, String status) {
        return (int) roomManagers.stream()
                .filter(roomManager -> roomManager.getStatus() != null && roomManager.getStatus().equalsIgnoreCase(status))
                .count();
    }

    private RoomMatrixResponse.RoomCell toRoomCell(RoomManager roomManager) {
        return RoomMatrixResponse.RoomCell.builder()
                .id(roomManager.getId())
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
