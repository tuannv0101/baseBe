package com.company.base.service.impl;

import com.company.base.dto.request.host.AssetMaintenanceHistoryRequest;
import com.company.base.dto.request.host.EquipmentCategoryRequest;
import com.company.base.dto.request.host.RoomAssetRequest;
import com.company.base.dto.response.host.AssetMaintenanceHistoryResponse;
import com.company.base.dto.response.host.EquipmentCategoryResponse;
import com.company.base.dto.response.host.RoomAssetResponse;
import com.company.base.entity.AssetMaintenanceHistory;
import com.company.base.entity.EquipmentCategory;
import com.company.base.entity.RoomAsset;
import com.company.base.exception.AppException;
import com.company.base.repository.host.AssetMaintenanceHistoryRepository;
import com.company.base.repository.host.EquipmentCategoryRepository;
import com.company.base.repository.host.RoomAssetRepository;
import com.company.base.service.AssetManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service implementation containing business logic for this module.
 */

@Service
@RequiredArgsConstructor
public class AssetManagementServiceImpl implements AssetManagementService {

    private final EquipmentCategoryRepository equipmentCategoryRepository;
    private final RoomAssetRepository roomAssetRepository;
    private final AssetMaintenanceHistoryRepository maintenanceHistoryRepository;

    @Override
    public EquipmentCategoryResponse createCategory(EquipmentCategoryRequest request) {
        EquipmentCategory entity = new EquipmentCategory();
        entity.setName(request.getName());
        entity.setBrand(request.getBrand());
        return toCategoryResponse(equipmentCategoryRepository.save(entity));
    }

    @Override
    public EquipmentCategoryResponse updateCategory(Long id, EquipmentCategoryRequest request) {
        EquipmentCategory entity = getCategoryEntity(id);
        entity.setName(request.getName());
        entity.setBrand(request.getBrand());
        return toCategoryResponse(equipmentCategoryRepository.save(entity));
    }

    @Override
    public EquipmentCategoryResponse getCategoryById(Long id) {
        return toCategoryResponse(getCategoryEntity(id));
    }

    @Override
    public List<EquipmentCategoryResponse> getAllCategories() {
        return equipmentCategoryRepository.findAllByOrderByNameAsc()
                .stream()
                .map(this::toCategoryResponse)
                .toList();
    }

    @Override
    public void deleteCategory(Long id) {
        if (!equipmentCategoryRepository.existsById(id)) {
            throw new AppException(HttpStatus.NOT_FOUND.value(), "Equipment category not found");
        }
        equipmentCategoryRepository.deleteById(id);
    }

    @Override
    public RoomAssetResponse createRoomAsset(RoomAssetRequest request) {
        RoomAsset entity = new RoomAsset();
        entity.setRoomId(request.getRoomId());
        entity.setCategoryId(request.getCategoryId());
        entity.setSerialNumber(request.getSerialNumber());
        entity.setStatus(request.getStatus());
        return toRoomAssetResponse(roomAssetRepository.save(entity));
    }

    @Override
    public RoomAssetResponse updateRoomAsset(Long id, RoomAssetRequest request) {
        RoomAsset entity = getRoomAssetEntity(id);
        entity.setRoomId(request.getRoomId());
        entity.setCategoryId(request.getCategoryId());
        entity.setSerialNumber(request.getSerialNumber());
        entity.setStatus(request.getStatus());
        return toRoomAssetResponse(roomAssetRepository.save(entity));
    }

    @Override
    public RoomAssetResponse getRoomAssetById(Long id) {
        return toRoomAssetResponse(getRoomAssetEntity(id));
    }

    @Override
    public List<RoomAssetResponse> getRoomAssetsByRoom(String roomId) {
        return roomAssetRepository.findByRoomId(roomId)
                .stream()
                .map(this::toRoomAssetResponse)
                .toList();
    }

    @Override
    public void deleteRoomAsset(Long id) {
        if (!roomAssetRepository.existsById(id)) {
            throw new AppException(HttpStatus.NOT_FOUND.value(), "Room asset not found");
        }
        roomAssetRepository.deleteById(id);
    }

    @Override
    public AssetMaintenanceHistoryResponse createMaintenanceHistory(Long roomAssetId, AssetMaintenanceHistoryRequest request) {
        if (!roomAssetRepository.existsById(roomAssetId)) {
            throw new AppException(HttpStatus.NOT_FOUND.value(), "Room asset not found");
        }

        AssetMaintenanceHistory entity = new AssetMaintenanceHistory();
        entity.setRoomAssetId(roomAssetId);
        applyMaintenanceUpdate(entity, request);
        return toMaintenanceResponse(maintenanceHistoryRepository.save(entity));
    }

    @Override
    public AssetMaintenanceHistoryResponse updateMaintenanceHistory(Long id, AssetMaintenanceHistoryRequest request) {
        AssetMaintenanceHistory entity = getMaintenanceEntity(id);
        applyMaintenanceUpdate(entity, request);
        return toMaintenanceResponse(maintenanceHistoryRepository.save(entity));
    }

    @Override
    public List<AssetMaintenanceHistoryResponse> getMaintenanceHistoryByRoomAsset(Long roomAssetId) {
        if (!roomAssetRepository.existsById(roomAssetId)) {
            throw new AppException(HttpStatus.NOT_FOUND.value(), "Room asset not found");
        }
        return maintenanceHistoryRepository.findByRoomAssetIdOrderByMaintenanceDateDescIdDesc(roomAssetId)
                .stream()
                .map(this::toMaintenanceResponse)
                .toList();
    }

    @Override
    public List<AssetMaintenanceHistoryResponse> getMaintenanceHistoryByRoom(String roomId) {
        List<Long> roomAssetIds = roomAssetRepository.findByRoomId(roomId).stream()
                .map(RoomAsset::getId)
                .toList();
        if (roomAssetIds.isEmpty()) {
            return List.of();
        }
        return maintenanceHistoryRepository.findByRoomAssetIdInOrderByMaintenanceDateDescIdDesc(roomAssetIds)
                .stream()
                .map(this::toMaintenanceResponse)
                .toList();
    }

    private void applyMaintenanceUpdate(AssetMaintenanceHistory entity, AssetMaintenanceHistoryRequest request) {
        entity.setMaintenanceDate(request.getMaintenanceDate());
        entity.setMaintenanceType(request.getMaintenanceType());
        entity.setDescription(request.getDescription());
        entity.setVendor(request.getVendor());
        entity.setCost(request.getCost());
        entity.setStatus(request.getStatus());
        entity.setNote(request.getNote());
    }

    private EquipmentCategory getCategoryEntity(Long id) {
        return equipmentCategoryRepository.findById(id)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND.value(), "Equipment category not found"));
    }

    private RoomAsset getRoomAssetEntity(Long id) {
        return roomAssetRepository.findById(id)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND.value(), "Room asset not found"));
    }

    private AssetMaintenanceHistory getMaintenanceEntity(Long id) {
        return maintenanceHistoryRepository.findById(id)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND.value(), "Maintenance history not found"));
    }

    private EquipmentCategoryResponse toCategoryResponse(EquipmentCategory entity) {
        return EquipmentCategoryResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .brand(entity.getBrand())
                .build();
    }

    private RoomAssetResponse toRoomAssetResponse(RoomAsset entity) {
        return RoomAssetResponse.builder()
                .id(entity.getId())
                .roomId(entity.getRoomId())
                .categoryId(entity.getCategoryId())
                .serialNumber(entity.getSerialNumber())
                .status(entity.getStatus())
                .build();
    }

    private AssetMaintenanceHistoryResponse toMaintenanceResponse(AssetMaintenanceHistory entity) {
        return AssetMaintenanceHistoryResponse.builder()
                .id(entity.getId())
                .roomAssetId(entity.getRoomAssetId())
                .maintenanceDate(entity.getMaintenanceDate())
                .maintenanceType(entity.getMaintenanceType())
                .description(entity.getDescription())
                .vendor(entity.getVendor())
                .cost(entity.getCost())
                .status(entity.getStatus())
                .note(entity.getNote())
                .build();
    }
}
