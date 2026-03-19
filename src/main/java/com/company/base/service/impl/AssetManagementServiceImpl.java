package com.company.base.service.impl;

import com.company.base.common.pagination.PageResponse;
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
    public EquipmentCategoryResponse updateCategory(String id, EquipmentCategoryRequest request) {
        EquipmentCategory entity = getCategoryEntity(id);
        entity.setName(request.getName());
        entity.setBrand(request.getBrand());
        return toCategoryResponse(equipmentCategoryRepository.save(entity));
    }

    @Override
    public EquipmentCategoryResponse getCategoryById(String id) {
        return toCategoryResponse(getCategoryEntity(id));
    }

    @Override
    public PageResponse<EquipmentCategoryResponse> getAllCategories(Pageable pageable) {
        Page<EquipmentCategoryResponse> page = equipmentCategoryRepository.findAllByOrderByNameAsc(pageable)
                .map(this::toCategoryResponse);
        return PageResponse.of(page);
    }

    @Override
    public void deleteCategory(String id) {
        EquipmentCategory entity = getCategoryEntity(id);
        entity.setDelYn("Y");
        equipmentCategoryRepository.save(entity);
    }

    @Override
    public RoomAssetResponse createRoomAsset(RoomAssetRequest request) {
        RoomAsset entity = new RoomAsset();
        entity.setRoomId(request.getRoomId());
        entity.setSerialNumber(request.getSerialNumber());
        entity.setStatus(request.getStatus());
        return toRoomAssetResponse(roomAssetRepository.save(entity));
    }

    @Override
    public RoomAssetResponse updateRoomAsset(String id, RoomAssetRequest request) {
        RoomAsset entity = getRoomAssetEntity(id);
        entity.setRoomId(request.getRoomId());
        entity.setSerialNumber(request.getSerialNumber());
        entity.setStatus(request.getStatus());
        return toRoomAssetResponse(roomAssetRepository.save(entity));
    }

    @Override
    public RoomAssetResponse getRoomAssetById(String id) {
        return toRoomAssetResponse(getRoomAssetEntity(id));
    }

    @Override
    public PageResponse<RoomAssetResponse> getRoomAssetsByRoom(String roomId, Pageable pageable) {
        Page<RoomAssetResponse> page = roomAssetRepository.findByRoomIdOrderByIdAsc(roomId, pageable)
                .map(this::toRoomAssetResponse);
        return PageResponse.of(page);
    }

    @Override
    public void deleteRoomAsset(String id) {
        RoomAsset entity = getRoomAssetEntity(id);
        entity.setDelYn("Y");
        roomAssetRepository.save(entity);
    }

    @Override
    public AssetMaintenanceHistoryResponse createMaintenanceHistory(String roomAssetId, AssetMaintenanceHistoryRequest request) {
        if (!roomAssetRepository.existsById(roomAssetId)) {
            throw new AppException(HttpStatus.NOT_FOUND.value(), "Room asset not found");
        }

        AssetMaintenanceHistory entity = new AssetMaintenanceHistory();
        entity.setRoomAssetId(roomAssetId);
        applyMaintenanceUpdate(entity, request);
        return toMaintenanceResponse(maintenanceHistoryRepository.save(entity));
    }

    @Override
    public AssetMaintenanceHistoryResponse updateMaintenanceHistory(String id, AssetMaintenanceHistoryRequest request) {
        AssetMaintenanceHistory entity = getMaintenanceEntity(id);
        applyMaintenanceUpdate(entity, request);
        return toMaintenanceResponse(maintenanceHistoryRepository.save(entity));
    }

    @Override
    public PageResponse<AssetMaintenanceHistoryResponse> getMaintenanceHistoryByRoomAsset(String roomAssetId, Pageable pageable) {
        if (!roomAssetRepository.existsById(roomAssetId)) {
            throw new AppException(HttpStatus.NOT_FOUND.value(), "Room asset not found");
        }
        Page<AssetMaintenanceHistoryResponse> page = maintenanceHistoryRepository
                .findByRoomAssetIdOrderByMaintenanceDateDescIdDesc(roomAssetId, pageable)
                .map(this::toMaintenanceResponse);
        return PageResponse.of(page);
    }

    @Override
    public PageResponse<AssetMaintenanceHistoryResponse> getMaintenanceHistoryByRoom(String roomId, Pageable pageable) {
        List<String> roomAssetIds = roomAssetRepository.findByRoomId(roomId).stream()
                .map(RoomAsset::getId)
                .toList();
        if (roomAssetIds.isEmpty()) {
            return PageResponse.of(Page.empty(pageable));
        }
        Page<AssetMaintenanceHistoryResponse> page = maintenanceHistoryRepository
                .findByRoomAssetIdInOrderByMaintenanceDateDescIdDesc(roomAssetIds, pageable)
                .map(this::toMaintenanceResponse);
        return PageResponse.of(page);
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

    private EquipmentCategory getCategoryEntity(String id) {
        return equipmentCategoryRepository.findById(id)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND.value(), "Equipment category not found"));
    }

    private RoomAsset getRoomAssetEntity(String id) {
        return roomAssetRepository.findById(id)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND.value(), "Room asset not found"));
    }

    private AssetMaintenanceHistory getMaintenanceEntity(String id) {
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


