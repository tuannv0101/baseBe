package com.company.base.service.impl;

import com.company.base.common.pagination.PageResponse;
import com.company.base.common.pagination.PaginationUtils;
import com.company.base.dto.request.host.ContractLiquidationRequest;
import com.company.base.dto.request.host.ContractRequest;
import com.company.base.dto.request.host.TenantRequest;
import com.company.base.dto.response.host.ContractLiquidationResponse;
import com.company.base.dto.response.host.ContractResponse;
import com.company.base.dto.response.host.TenantResponse;
import com.company.base.entity.Contract;
import com.company.base.entity.ContractLiquidationHistory;
import com.company.base.entity.FileMetadata;
import com.company.base.entity.Tenant;
import com.company.base.exception.AppException;
import com.company.base.repository.host.ContractLiquidationHistoryRepository;
import com.company.base.repository.host.ContractRepository;
import com.company.base.repository.host.TenantRepository;
import com.company.base.service.FileService;
import com.company.base.service.TenancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/**
 * Service implementation containing business logic for this module.
 */

@Service
@RequiredArgsConstructor
public class TenancyServiceImpl implements TenancyService {

    private static final String STATUS_ACTIVE = "ACTIVE";
    private static final String STATUS_TERMINATED = "TERMINATED";
    private static final Set<String> PENDING_STATUSES = Set.of("PENDING_SIGN", "PENDING_RENEWAL");

    private final TenantRepository tenantRepository;
    private final ContractRepository contractRepository;
    private final ContractLiquidationHistoryRepository liquidationHistoryRepository;
    private final FileService fileService;

    @Override
    public TenantResponse createTenant(TenantRequest request) {
        Tenant entity = new Tenant();
        applyTenantUpdate(entity, request);
        return toTenantResponse(tenantRepository.save(entity));
    }

    @Override
    public TenantResponse updateTenant(Long id, TenantRequest request) {
        Tenant entity = getTenantEntity(id);
        applyTenantUpdate(entity, request);
        return toTenantResponse(tenantRepository.save(entity));
    }

    @Override
    public TenantResponse getTenantById(Long id) {
        return toTenantResponse(getTenantEntity(id));
    }

    @Override
    public TenantResponse getTenantByIdCardNumber(String idCardNumber) {
        if (idCardNumber == null || idCardNumber.isBlank()) {
            throw new AppException(HttpStatus.BAD_REQUEST.value(), "idCardNumber is required");
        }
        Tenant entity = tenantRepository.findByIdCardNumber(idCardNumber.trim())
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND.value(), "Tenant not found"));
        return toTenantResponse(entity);
    }

    @Override
    public PageResponse<TenantResponse> getAllTenants(Pageable pageable) {
        Page<TenantResponse> page = tenantRepository.findAllByOrderByFullNameAsc(pageable)
                .map(this::toTenantResponse);
        return PageResponse.of(page);
    }

    @Override
    public PageResponse<TenantResponse> getTenantsByTemporaryResidenceStatus(boolean declared, Pageable pageable) {
        Page<TenantResponse> page = tenantRepository.findByTemporaryResidenceDeclaredOrderByFullNameAsc(declared, pageable)
                .map(this::toTenantResponse);
        return PageResponse.of(page);
    }

    @Override
    public void deleteTenant(Long id) {
        Tenant entity = getTenantEntity(id);
        entity.setDelYn("Y");
        tenantRepository.save(entity);
    }

    @Override
    public TenantResponse uploadTenantPortrait(Long tenantId, MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new AppException(HttpStatus.BAD_REQUEST.value(), "File is required");
        }

        FileMetadata metadata = fileService.upload(file);
        Tenant entity = getTenantEntity(tenantId);
        entity.setPortraitImageId(metadata.getId());
        return toTenantResponse(tenantRepository.save(entity));
    }

    @Override
    public ContractResponse createContract(ContractRequest request) {
        Contract entity = new Contract();
        applyContractUpdate(entity, request);
        return toContractResponse(contractRepository.save(entity));
    }

    @Override
    public ContractResponse updateContract(Long id, ContractRequest request) {
        Contract entity = getContractEntity(id);
        applyContractUpdate(entity, request);
        return toContractResponse(contractRepository.save(entity));
    }

    @Override
    public ContractResponse getContractById(Long id) {
        return toContractResponse(getContractEntity(id));
    }

    @Override
    public PageResponse<ContractResponse> getAllContracts(Pageable pageable) {
        Page<ContractResponse> page = contractRepository.findAllByOrderByStartDateDescIdDesc(pageable)
                .map(this::toContractResponse);
        return PageResponse.of(page);
    }

    @Override
    public PageResponse<ContractResponse> getEffectiveContracts(Pageable pageable) {
        LocalDate today = LocalDate.now();
        List<ContractResponse> effective = contractRepository.findByStatusIgnoreCaseOrderByStartDateDescIdDesc(STATUS_ACTIVE)
                .stream()
                .filter(contract -> contract.getStartDate() == null || !contract.getStartDate().isAfter(today))
                .filter(contract -> contract.getEndDate() == null || !contract.getEndDate().isBefore(today))
                .map(this::toContractResponse)
                .toList();
        return PaginationUtils.paginateList(effective, pageable);
    }

    @Override
    public PageResponse<ContractResponse> getPendingContracts(Pageable pageable) {
        Page<ContractResponse> page = contractRepository.findByStatusInOrderByStartDateDescIdDesc(PENDING_STATUSES, pageable)
                .map(this::toContractResponse);
        return PageResponse.of(page);
    }

    @Override
    public void deleteContract(Long id) {
        Contract entity = getContractEntity(id);
        entity.setDelYn("Y");
        contractRepository.save(entity);
    }

    @Override
    public ContractLiquidationResponse liquidateContract(Long contractId, ContractLiquidationRequest request) {
        Contract contract = getContractEntity(contractId);
        contract.setStatus(STATUS_TERMINATED);
        contractRepository.save(contract);

        ContractLiquidationHistory history = new ContractLiquidationHistory();
        history.setContractId(contract.getId());
        history.setRoomId(contract.getRoomId());
        history.setTenantId(contract.getTenantId());
        history.setLiquidationDate(request.getLiquidationDate() != null ? request.getLiquidationDate() : LocalDate.now());
        history.setReason(request.getReason());
        history.setNote(request.getNote());

        return toLiquidationResponse(liquidationHistoryRepository.save(history));
    }

    @Override
    public PageResponse<ContractLiquidationResponse> getLiquidationHistory(Pageable pageable) {
        Page<ContractLiquidationResponse> page = liquidationHistoryRepository.findAllByOrderByLiquidationDateDescIdDesc(pageable)
                .map(this::toLiquidationResponse);
        return PageResponse.of(page);
    }

    private void applyTenantUpdate(Tenant entity, TenantRequest request) {
        entity.setFullName(request.getFullName());
        entity.setPhone(request.getPhone());
        entity.setEmail(request.getEmail());
        entity.setIdCardNumber(request.getIdCardNumber());
        entity.setPortraitImageId(request.getPortraitImageId());

        boolean declared = Boolean.TRUE.equals(request.getTemporaryResidenceDeclared());
        entity.setTemporaryResidenceDeclared(declared);
        if (declared) {
            entity.setTemporaryResidenceDeclaredAt(
                    request.getTemporaryResidenceDeclaredAt() != null ? request.getTemporaryResidenceDeclaredAt() : LocalDate.now()
            );
        } else {
            entity.setTemporaryResidenceDeclaredAt(null);
        }
    }

    private void applyContractUpdate(Contract entity, ContractRequest request) {
        entity.setRoomId(request.getRoomId());
        entity.setTenantId(request.getTenantId());
        entity.setStartDate(request.getStartDate());
        entity.setEndDate(request.getEndDate());
        entity.setDepositAmount(request.getDepositAmount());
        entity.setActualRent(request.getActualRent());
        entity.setStatus(normalizeStatus(request.getStatus()));
    }

    private String normalizeStatus(String status) {
        return status == null ? null : status.trim().toUpperCase();
    }

    private Tenant getTenantEntity(Long id) {
        return tenantRepository.findById(id)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND.value(), "Tenant not found"));
    }

    private Contract getContractEntity(Long id) {
        return contractRepository.findById(id)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND.value(), "Contract not found"));
    }

    private TenantResponse toTenantResponse(Tenant entity) {
        return TenantResponse.builder()
                .id(entity.getId())
                .fullName(entity.getFullName())
                .phone(entity.getPhone())
                .email(entity.getEmail())
                .idCardNumber(entity.getIdCardNumber())
                .portraitImageId(entity.getPortraitImageId())
                .portraitImageUrl(entity.getPortraitImageId() != null ? "/api/v1/host/files/" + entity.getPortraitImageId() : null)
                .temporaryResidenceDeclared(Boolean.TRUE.equals(entity.getTemporaryResidenceDeclared()))
                .temporaryResidenceDeclaredAt(entity.getTemporaryResidenceDeclaredAt())
                .build();
    }

    private ContractResponse toContractResponse(Contract entity) {
        return ContractResponse.builder()
                .id(entity.getId())
                .roomId(entity.getRoomId())
                .tenantId(entity.getTenantId())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .depositAmount(entity.getDepositAmount())
                .actualRent(entity.getActualRent())
                .status(entity.getStatus())
                .build();
    }

    private ContractLiquidationResponse toLiquidationResponse(ContractLiquidationHistory entity) {
        return ContractLiquidationResponse.builder()
                .id(entity.getId())
                .contractId(entity.getContractId())
                .roomId(entity.getRoomId())
                .tenantId(entity.getTenantId())
                .liquidationDate(entity.getLiquidationDate())
                .reason(entity.getReason())
                .note(entity.getNote())
                .build();
    }
}
