package com.company.base.service.impl;

import com.company.base.dto.request.ContractLiquidationRequest;
import com.company.base.dto.request.ContractRequest;
import com.company.base.dto.request.TenantRequest;
import com.company.base.dto.response.ContractLiquidationResponse;
import com.company.base.dto.response.ContractResponse;
import com.company.base.dto.response.TenantResponse;
import com.company.base.entity.Contract;
import com.company.base.entity.ContractLiquidationHistory;
import com.company.base.entity.Tenant;
import com.company.base.exception.AppException;
import com.company.base.repository.ContractLiquidationHistoryRepository;
import com.company.base.repository.ContractRepository;
import com.company.base.repository.TenantRepository;
import com.company.base.service.TenancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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
    public List<TenantResponse> getAllTenants() {
        return tenantRepository.findAllByOrderByFullNameAsc()
                .stream()
                .map(this::toTenantResponse)
                .toList();
    }

    @Override
    public List<TenantResponse> getTenantsByTemporaryResidenceStatus(boolean declared) {
        return tenantRepository.findByTemporaryResidenceDeclaredOrderByFullNameAsc(declared)
                .stream()
                .map(this::toTenantResponse)
                .toList();
    }

    @Override
    public void deleteTenant(Long id) {
        if (!tenantRepository.existsById(id)) {
            throw new AppException(HttpStatus.NOT_FOUND.value(), "Tenant not found");
        }
        tenantRepository.deleteById(id);
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
    public List<ContractResponse> getAllContracts() {
        return contractRepository.findAll().stream()
                .map(this::toContractResponse)
                .toList();
    }

    @Override
    public List<ContractResponse> getEffectiveContracts() {
        LocalDate today = LocalDate.now();
        return contractRepository.findByStatusIgnoreCaseOrderByStartDateDescIdDesc(STATUS_ACTIVE)
                .stream()
                .filter(contract -> contract.getStartDate() == null || !contract.getStartDate().isAfter(today))
                .filter(contract -> contract.getEndDate() == null || !contract.getEndDate().isBefore(today))
                .map(this::toContractResponse)
                .toList();
    }

    @Override
    public List<ContractResponse> getPendingContracts() {
        return contractRepository.findByStatusInOrderByStartDateDescIdDesc(PENDING_STATUSES)
                .stream()
                .map(this::toContractResponse)
                .toList();
    }

    @Override
    public void deleteContract(Long id) {
        if (!contractRepository.existsById(id)) {
            throw new AppException(HttpStatus.NOT_FOUND.value(), "Contract not found");
        }
        contractRepository.deleteById(id);
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
    public List<ContractLiquidationResponse> getLiquidationHistory() {
        return liquidationHistoryRepository.findAllByOrderByLiquidationDateDescIdDesc()
                .stream()
                .map(this::toLiquidationResponse)
                .toList();
    }

    private void applyTenantUpdate(Tenant entity, TenantRequest request) {
        entity.setFullName(request.getFullName());
        entity.setPhone(request.getPhone());
        entity.setEmail(request.getEmail());
        entity.setIdCardNumber(request.getIdCardNumber());
        entity.setPortraitImageId(request.getPortraitImageId());
        entity.setPasswordHash(request.getPasswordHash());

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
                .portraitImageUrl(entity.getPortraitImageId() != null ? "/api/v1/files/" + entity.getPortraitImageId() : null)
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
