package com.company.base.service.impl;

import com.company.base.dto.request.host.PropertyRequest;
import com.company.base.dto.response.host.PropertyResponse;
import com.company.base.dto.response.host.RoomMatrixResponse;
import com.company.base.entity.Properties;
import com.company.base.entity.Room;
import com.company.base.exception.AppException;
import com.company.base.repository.host.PropertiesRepository;
import com.company.base.repository.host.RoomRepository;
import com.company.base.service.PropertyManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Service implementation containing business logic for this module.
 */

@Service
@RequiredArgsConstructor
public class PropertyManagementServiceImpl implements PropertyManagementService {

    private final PropertiesRepository propertiesRepository;
    private final RoomRepository roomRepository;

    @Override
    public PropertyResponse createProperty(PropertyRequest request) {
        Properties entity = new Properties();
        entity.setName(request.getName());
        entity.setAddress(request.getAddress());
        entity.setTotalFloors(request.getTotalFloors());
        return toPropertyResponse(propertiesRepository.save(entity));
    }

    @Override
    public PropertyResponse updateProperty(Long id, PropertyRequest request) {
        Properties entity = getPropertyEntity(id);
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
    public List<PropertyResponse> getAllProperties() {
        return propertiesRepository.findAllByOrderByNameAsc()
                .stream()
                .map(this::toPropertyResponse)
                .toList();
    }

    @Override
    public void deleteProperty(Long id) {
        if (!propertiesRepository.existsById(id)) {
            throw new AppException(HttpStatus.NOT_FOUND.value(), "Property not found");
        }
        propertiesRepository.deleteById(id);
    }

    @Override
    public List<RoomMatrixResponse> getRoomMatrix(Long propertyId) {
        List<Properties> properties = propertyId == null
                ? propertiesRepository.findAllByOrderByNameAsc()
                : List.of(getPropertyEntity(propertyId));

        List<RoomMatrixResponse> result = new ArrayList<>();
        for (Properties property : properties) {
            List<Room> rooms = roomRepository.findByPropertiesIdOrderByFloorAscRoomNumberAsc(property.getId());
            result.add(buildRoomMatrix(property, rooms));
        }
        return result;
    }

    private RoomMatrixResponse buildRoomMatrix(Properties property, List<Room> rooms) {
        Map<Integer, List<Room>> groupedByFloor = rooms.stream()
                .collect(Collectors.groupingBy(Room::getFloor, TreeMap::new, Collectors.toList()));

        List<RoomMatrixResponse.FloorRow> floors = groupedByFloor.entrySet()
                .stream()
                .sorted(Comparator.comparingInt(Map.Entry::getKey))
                .map(entry -> RoomMatrixResponse.FloorRow.builder()
                        .floor(entry.getKey())
                        .rooms(entry.getValue().stream().map(this::toRoomCell).toList())
                        .build())
                .toList();

        int total = rooms.size();
        int available = countByStatus(rooms, "AVAILABLE");
        int occupied = countByStatus(rooms, "OCCUPIED");
        int maintenance = countByStatus(rooms, "MAINTENANCE");

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

    private int countByStatus(List<Room> rooms, String status) {
        return (int) rooms.stream()
                .filter(room -> room.getStatus() != null && room.getStatus().equalsIgnoreCase(status))
                .count();
    }

    private RoomMatrixResponse.RoomCell toRoomCell(Room room) {
        return RoomMatrixResponse.RoomCell.builder()
                .id(room.getId())
                .roomNumber(room.getRoomNumber())
                .status(room.getStatus())
                .price(room.getPrice())
                .area(room.getArea())
                .build();
    }

    private PropertyResponse toPropertyResponse(Properties entity) {
        return PropertyResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .address(entity.getAddress())
                .totalFloors(entity.getTotalFloors())
                .build();
    }

    private Properties getPropertyEntity(Long id) {
        return propertiesRepository.findById(id)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND.value(), "Property not found"));
    }
}
