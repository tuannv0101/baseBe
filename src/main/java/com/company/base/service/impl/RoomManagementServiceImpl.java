package com.company.base.service.impl;

import com.company.base.common.pagination.PageResponse;
import com.company.base.dto.request.host.RoomRequest;
import com.company.base.dto.request.host.roomManager.RoomAssetCreateReqDTO;
import com.company.base.dto.request.host.roomManager.RoomManagerCreateReqDTO;
import com.company.base.dto.response.host.RoomResponse;
import com.company.base.dto.response.host.roomManager.RoomAssetDetailResDTO;
import com.company.base.dto.response.host.roomManager.RoomDetailResDTO;
import com.company.base.entity.Contract;
import com.company.base.entity.PropertiesManager;
import com.company.base.entity.RoomAsset;
import com.company.base.entity.RoomManager;
import com.company.base.entity.Tenant;
import com.company.base.exception.AppException;
import com.company.base.repository.host.ContractRepository;
import com.company.base.repository.host.PropertiesRepository;
import com.company.base.repository.host.RoomAssetRepository;
import com.company.base.repository.host.RoomManagementRepository;
import com.company.base.repository.host.TenantRepository;
import com.company.base.service.RoomManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RoomManagementServiceImpl implements RoomManagementService {
    private final RoomManagementRepository roomManagementRepository;
    private final PropertiesRepository propertiesRepository;
    private final RoomAssetRepository roomAssetRepository;
    private final ContractRepository contractRepository;
    private final TenantRepository tenantRepository;
    @Override
    @Transactional
    public Long createRoom(RoomManagerCreateReqDTO roomManagerCreateReqDTO) {
        if (roomManagerCreateReqDTO.getPropertiesId() == null) {
            throw new AppException(HttpStatus.BAD_REQUEST.value(), "propertyId is required");
        }

        PropertiesManager property = propertiesRepository.findById(roomManagerCreateReqDTO.getPropertiesId())
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND.value(), "Property not found"));

        RoomManager entityRoomManager = new RoomManager();
        entityRoomManager.setArea(roomManagerCreateReqDTO.getArea());
        entityRoomManager.setRoomNumber(roomManagerCreateReqDTO.getRoomNumber());
        entityRoomManager.setPrice(roomManagerCreateReqDTO.getPrice());
        entityRoomManager.setStatus(normalize(roomManagerCreateReqDTO.getStatus()));
        entityRoomManager.setFloor(roomManagerCreateReqDTO.getFloor());
        entityRoomManager.setPropertiesId(property.getId());
        entityRoomManager.setDelYn("N");
        entityRoomManager.setTypeRoom(roomManagerCreateReqDTO.getTypeRoom());
        roomManagementRepository.save(entityRoomManager);

        if (roomManagerCreateReqDTO.getRoomAssetCreateReqDTOS() != null
                && !roomManagerCreateReqDTO.getRoomAssetCreateReqDTOS().isEmpty()) {
            List<RoomAsset> assets = new ArrayList<>(roomManagerCreateReqDTO.getRoomAssetCreateReqDTOS().size());
            Long roomId = entityRoomManager.getId();

            for (RoomAssetCreateReqDTO a : roomManagerCreateReqDTO.getRoomAssetCreateReqDTOS()) {
                if (a == null) {
                    continue;
                }
                RoomAsset asset = new RoomAsset();
                asset.setRoomId(roomId);
                asset.setName(a.getName());
                asset.setBrand(a.getBrand());
                asset.setSerialNumber(a.getSerialNumber());
                asset.setStatus(normalize(a.getStatus()));
                asset.setDelYn("N");
                assets.add(asset);
            }

            if (!assets.isEmpty()) {
                roomAssetRepository.saveAll(assets);
            }
        }
        return entityRoomManager.getId();
    }

    @Override
    @Transactional
    public RoomResponse updateRoom(Long id, RoomRequest request) {
        RoomManager entity = getRoomEntity(id);
        applyUpdate(entity, request);
        RoomManager saved = roomManagementRepository.save(entity);

        // Replace assets if caller provides the list (including empty list meaning clear assets).
        if (request.getRoomAssetCreateReqDTOS() != null) {
            Long roomId =saved.getId();

            List<RoomAsset> existing = roomAssetRepository.findByRoomIdAndDelYn(roomId, "N");

            // Key active assets by serialNumber (best-effort). Items without serialNumber cannot be diffed reliably.
            Map<String, RoomAsset> existingBySerial = new HashMap<>();
            for (RoomAsset a : existing) {
                String serial = a.getSerialNumber();
                if (serial != null && !serial.isBlank()) {
                    existingBySerial.put(serial.trim(), a);
                }
            }

            Set<String> incomingSerials = new HashSet<>();
            List<RoomAsset> toInsert = new ArrayList<>();
            List<RoomAsset> toUpdate = new ArrayList<>();

            for (RoomAssetCreateReqDTO a : request.getRoomAssetCreateReqDTOS()) {
                if (a == null) {
                    continue;
                }

                String serial = a.getSerialNumber() != null ? a.getSerialNumber().trim() : null;
                String normalizedStatus = normalize(a.getStatus());

                if (serial != null && !serial.isBlank()) {
                    incomingSerials.add(serial);
                    RoomAsset matched = existingBySerial.get(serial);
                    if (matched != null) {
                        // Update only changed fields.
                        matched.setName(a.getName());
                        matched.setBrand(a.getBrand());
                        matched.setStatus(normalizedStatus);
                        toUpdate.add(matched);
                    } else {
                        RoomAsset asset = new RoomAsset();
                        asset.setRoomId(roomId);
                        asset.setName(a.getName());
                        asset.setBrand(a.getBrand());
                        asset.setSerialNumber(serial);
                        asset.setStatus(normalizedStatus);
                        asset.setDelYn("N");
                        toInsert.add(asset);
                    }
                } else {
                    // No serialNumber: treat as a new asset row.
                    RoomAsset asset = new RoomAsset();
                    asset.setRoomId(roomId);
                    asset.setName(a.getName());
                    asset.setBrand(a.getBrand());
                    asset.setSerialNumber(a.getSerialNumber());
                    asset.setStatus(normalizedStatus);
                    asset.setDelYn("N");
                    toInsert.add(asset);
                }
            }

            // Soft-delete assets (delYn=N) that are missing from incoming (by serialNumber).
            // If incoming list is empty, this clears all active assets.
            if (request.getRoomAssetCreateReqDTOS().isEmpty()) {
                for (RoomAsset a : existing) {
                    a.setDelYn("Y");
                }
                if (!existing.isEmpty()) {
                    roomAssetRepository.saveAll(existing);
                }
            } else if (!incomingSerials.isEmpty()) {
                List<RoomAsset> toDelete = new ArrayList<>();
                for (RoomAsset a : existing) {
                    String serial = a.getSerialNumber();
                    if (serial != null && !serial.isBlank() && !incomingSerials.contains(serial.trim())) {
                        a.setDelYn("Y");
                        toDelete.add(a);
                    }
                }
                if (!toDelete.isEmpty()) {
                    roomAssetRepository.saveAll(toDelete);
                }
            }

            if (!toUpdate.isEmpty()) {
                roomAssetRepository.saveAll(toUpdate);
            }
            if (!toInsert.isEmpty()) {
                roomAssetRepository.saveAll(toInsert);
            }
        }

        return toResponse(saved);
    }

    @Override
    public RoomResponse getRoomById(Long id) {
        return toResponse(getRoomEntity(id));
    }

    @Override
    public RoomDetailResDTO getRoomDetail(Long id) {
        RoomManager room = getRoomEntity(id);

        PropertiesManager property = null;
        if (room.getPropertiesId() != null) {
            property = propertiesRepository.findById(room.getPropertiesId()).orElse(null);
        }

        TenantInfo tenantInfo = getActiveTenantInfoByRoomId(room.getId());

        List<RoomAssetDetailResDTO> assets = roomAssetRepository.findByRoomIdAndDelYn(room.getId(), "N")
                .stream()
                .map(this::toAssetDetail)
                .toList();

        return RoomDetailResDTO.builder()
                .roomId(room.getId())
                .propertyId(room.getPropertiesId())
                .propertyName(property != null ? property.getName() : null)
                .propertyAddress(property != null ? property.getAddress() : null)
                .tenantId(tenantInfo.tenantId)
                .tenantFullName(tenantInfo.tenantFullName)
                .area(room.getArea())
                .floor(room.getFloor())
                .price(room.getPrice())
                .roomNumber(room.getRoomNumber())
                .status(room.getStatus())
                .type(room.getTypeRoom())
                .assets(assets)
                .build();
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
        RoomManager entity = getRoomEntity(id);
        entity.setDelYn("Y");
        roomManagementRepository.save(entity);
    }

    private void applyUpdate(RoomManager entity, RoomRequest request) {
        if (request.getPropertyId() != null) {
            PropertiesManager property = propertiesRepository.findById(request.getPropertyId())
                    .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND.value(), "Property not found"));
            entity.setPropertiesId(property.getId());
        }
        entity.setArea(request.getArea());
        entity.setFloor(request.getFloor());
        entity.setPrice(request.getPrice());
        entity.setRoomNumber(request.getRoomNumber());
        entity.setStatus(normalize(request.getStatus()));
        if (request.getType() != null) {
            entity.setTypeRoom(request.getType().trim());
        }
    }

    private String normalize(String value) {
        return value == null ? null : value.trim().toUpperCase();
    }

    private RoomManager getRoomEntity(Long id) {
        return roomManagementRepository.findById(id)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND.value(), "Room not found"));
    }

    private RoomResponse toResponse(RoomManager entity) {
        TenantInfo tenantInfo = getActiveTenantInfoByRoomId(entity.getId());
        return RoomResponse.builder()
                .roomId(entity.getId())
                .propertyId(entity.getPropertiesId())
                .tenantId(tenantInfo.tenantId)
                .tenantFullName(tenantInfo.tenantFullName)
                .area(entity.getArea())
                .floor(entity.getFloor())
                .price(entity.getPrice())
                .roomNumber(entity.getRoomNumber())
                .status(entity.getStatus())
                .build();
    }

    private RoomAssetDetailResDTO toAssetDetail(RoomAsset a) {
        return RoomAssetDetailResDTO.builder()
                .id(a.getId())
                .name(a.getName())
                .brand(a.getBrand())
                .serialNumber(a.getSerialNumber())
                .status(a.getStatus())
                .build();
    }

    private TenantInfo getActiveTenantInfoByRoomId(Long roomId) {
        if (roomId == null) {
            return new TenantInfo(null, null);
        }

        Contract active = contractRepository
                .findFirstByRoomIdAndStatusIgnoreCaseOrderByStartDateDescIdDesc(roomId, "ACTIVE")
                .orElse(null);
        if (active == null || active.getTenantId() == null) {
            return new TenantInfo(null, null);
        }

        Tenant tenant = tenantRepository.findById(active.getTenantId()).orElse(null);
        return new TenantInfo(active.getTenantId(), tenant != null ? tenant.getFullName() : null);
    }

    private record TenantInfo(Long tenantId, String tenantFullName) {
    }
}
