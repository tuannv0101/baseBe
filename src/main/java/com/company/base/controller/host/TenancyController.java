package com.company.base.controller.host;

import com.company.base.common.ApiResponse;
import com.company.base.dto.request.host.ContractLiquidationRequest;
import com.company.base.dto.request.host.ContractRequest;
import com.company.base.dto.request.host.TenantRequest;
import com.company.base.dto.response.host.ContractLiquidationResponse;
import com.company.base.dto.response.host.ContractResponse;
import com.company.base.dto.response.host.TenantResponse;
import com.company.base.service.TenancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller that exposes API endpoints for this module.
 */

@RestController
@RequestMapping("/api/v1/tenancy")
@RequiredArgsConstructor
public class TenancyController {

    private final TenancyService tenancyService;

    @PostMapping("/tenants")
    public ApiResponse<TenantResponse> createTenant(@RequestBody TenantRequest request) {
        return ApiResponse.success(tenancyService.createTenant(request));
    }

    @PutMapping("/tenants/{id}")
    public ApiResponse<TenantResponse> updateTenant(@PathVariable Long id, @RequestBody TenantRequest request) {
        return ApiResponse.success(tenancyService.updateTenant(id, request));
    }

    @GetMapping("/tenants/{id}")
    public ApiResponse<TenantResponse> getTenantById(@PathVariable Long id) {
        return ApiResponse.success(tenancyService.getTenantById(id));
    }

    @GetMapping("/tenants")
    public ApiResponse<List<TenantResponse>> getAllTenants() {
        return ApiResponse.success(tenancyService.getAllTenants());
    }

    @DeleteMapping("/tenants/{id}")
    public ApiResponse<Void> deleteTenant(@PathVariable Long id) {
        tenancyService.deleteTenant(id);
        return ApiResponse.success(null);
    }

    @GetMapping("/temporary-residence")
    public ApiResponse<List<TenantResponse>> getTenantsByTemporaryResidence(@RequestParam boolean declared) {
        return ApiResponse.success(tenancyService.getTenantsByTemporaryResidenceStatus(declared));
    }

    @PostMapping("/contracts")
    public ApiResponse<ContractResponse> createContract(@RequestBody ContractRequest request) {
        return ApiResponse.success(tenancyService.createContract(request));
    }

    @PutMapping("/contracts/{id}")
    public ApiResponse<ContractResponse> updateContract(@PathVariable Long id, @RequestBody ContractRequest request) {
        return ApiResponse.success(tenancyService.updateContract(id, request));
    }

    @GetMapping("/contracts/{id}")
    public ApiResponse<ContractResponse> getContractById(@PathVariable Long id) {
        return ApiResponse.success(tenancyService.getContractById(id));
    }

    @GetMapping("/contracts")
    public ApiResponse<List<ContractResponse>> getAllContracts() {
        return ApiResponse.success(tenancyService.getAllContracts());
    }

    @GetMapping("/contracts/effective")
    public ApiResponse<List<ContractResponse>> getEffectiveContracts() {
        return ApiResponse.success(tenancyService.getEffectiveContracts());
    }

    @GetMapping("/contracts/pending")
    public ApiResponse<List<ContractResponse>> getPendingContracts() {
        return ApiResponse.success(tenancyService.getPendingContracts());
    }

    @DeleteMapping("/contracts/{id}")
    public ApiResponse<Void> deleteContract(@PathVariable Long id) {
        tenancyService.deleteContract(id);
        return ApiResponse.success(null);
    }

    @PostMapping("/contracts/{id}/liquidate")
    public ApiResponse<ContractLiquidationResponse> liquidateContract(
            @PathVariable Long id,
            @RequestBody ContractLiquidationRequest request
    ) {
        return ApiResponse.success(tenancyService.liquidateContract(id, request));
    }

    @GetMapping("/contracts/liquidation-history")
    public ApiResponse<List<ContractLiquidationResponse>> getLiquidationHistory() {
        return ApiResponse.success(tenancyService.getLiquidationHistory());
    }
}
