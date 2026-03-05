package com.company.base.service;

import com.company.base.dto.request.ContractLiquidationRequest;
import com.company.base.dto.request.ContractRequest;
import com.company.base.dto.request.TenantRequest;
import com.company.base.dto.response.ContractLiquidationResponse;
import com.company.base.dto.response.ContractResponse;
import com.company.base.dto.response.TenantResponse;

import java.util.List;
/**
 * Service contract defining operations for this module.
 */

public interface TenancyService {
    TenantResponse createTenant(TenantRequest request);
    TenantResponse updateTenant(Long id, TenantRequest request);
    TenantResponse getTenantById(Long id);
    List<TenantResponse> getAllTenants();
    List<TenantResponse> getTenantsByTemporaryResidenceStatus(boolean declared);
    void deleteTenant(Long id);

    ContractResponse createContract(ContractRequest request);
    ContractResponse updateContract(Long id, ContractRequest request);
    ContractResponse getContractById(Long id);
    List<ContractResponse> getAllContracts();
    List<ContractResponse> getEffectiveContracts();
    List<ContractResponse> getPendingContracts();
    void deleteContract(Long id);

    ContractLiquidationResponse liquidateContract(Long contractId, ContractLiquidationRequest request);
    List<ContractLiquidationResponse> getLiquidationHistory();
}
