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
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.company.base.common.pagination.PageResponse;

/**
 * REST controller that exposes API endpoints for this module.
 */

@RestController
@RequestMapping("/api/v1/host/tenancy")
@RequiredArgsConstructor
public class TenancyController {

    private final TenancyService tenancyService;

    @PostMapping("/tenants")
    public ApiResponse<TenantResponse> createTenant(@RequestBody TenantRequest request) {
        return ApiResponse.success(tenancyService.createTenant(request));
    }

    @PutMapping("/tenants/{id}")
    public ApiResponse<TenantResponse> updateTenant(@PathVariable String id, @RequestBody TenantRequest request) {
        return ApiResponse.success(tenancyService.updateTenant(id, request));
    }

    @GetMapping("/tenants/{id}")
    public ApiResponse<TenantResponse> getTenantById(@PathVariable String id) {
        return ApiResponse.success(tenancyService.getTenantById(id));
    }

    @GetMapping("/tenants/by-id-card-number")
    public ApiResponse<TenantResponse> getTenantByIdCardNumber(@RequestParam("idCardNumber") String idCardNumber) {
        return ApiResponse.success(tenancyService.getTenantByIdCardNumber(idCardNumber));
    }

    @GetMapping("/tenants")
    public ApiResponse<PageResponse<TenantResponse>> getAllTenants(@PageableDefault(size = 20) Pageable pageable) {
        return ApiResponse.success(tenancyService.getAllTenants(pageable));
    }

    @PutMapping("/tenants/{id}/delete")
    public ApiResponse<Void> deleteTenant(@PathVariable String id) {
        tenancyService.deleteTenant(id);
        return ApiResponse.success(null);
    }

    @PostMapping("/tenants/{id}/portrait")
    public ApiResponse<TenantResponse> uploadTenantPortrait(
            @PathVariable String id,
            @RequestParam("file") MultipartFile file
    ) {
        return ApiResponse.success(tenancyService.uploadTenantPortrait(id, file));
    }

    @GetMapping("/temporary-residence")
    public ApiResponse<PageResponse<TenantResponse>> getTenantsByTemporaryResidence(
            @RequestParam boolean declared,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        return ApiResponse.success(tenancyService.getTenantsByTemporaryResidenceStatus(declared, pageable));
    }

    @PostMapping("/contracts")
    public ApiResponse<ContractResponse> createContract(@RequestBody ContractRequest request) {
        return ApiResponse.success(tenancyService.createContract(request));
    }

    @PutMapping("/contracts/{id}")
    public ApiResponse<ContractResponse> updateContract(@PathVariable String id, @RequestBody ContractRequest request) {
        return ApiResponse.success(tenancyService.updateContract(id, request));
    }

    @GetMapping("/contracts/{id}")
    public ApiResponse<ContractResponse> getContractById(@PathVariable String id) {
        return ApiResponse.success(tenancyService.getContractById(id));
    }

    @GetMapping("/contracts")
    public ApiResponse<PageResponse<ContractResponse>> getAllContracts(
            @RequestParam(required = false) String textSearch,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        return ApiResponse.success(tenancyService.getAllContracts(textSearch, pageable));
    }

    @GetMapping("/contracts/effective")
    public ApiResponse<PageResponse<ContractResponse>> getEffectiveContracts(@PageableDefault(size = 20) Pageable pageable) {
        return ApiResponse.success(tenancyService.getEffectiveContracts(pageable));
    }

    @GetMapping("/contracts/pending")
    public ApiResponse<PageResponse<ContractResponse>> getPendingContracts(@PageableDefault(size = 20) Pageable pageable) {
        return ApiResponse.success(tenancyService.getPendingContracts(pageable));
    }

    @PutMapping("/contracts/{id}/delete")
    public ApiResponse<Void> deleteContract(@PathVariable String id) {
        tenancyService.deleteContract(id);
        return ApiResponse.success(null);
    }

    @PostMapping("/contracts/{id}/liquidate")
    public ApiResponse<ContractLiquidationResponse> liquidateContract(
            @PathVariable String id,
            @RequestBody ContractLiquidationRequest request
    ) {
        return ApiResponse.success(tenancyService.liquidateContract(id, request));
    }

    @GetMapping("/contracts/liquidation-history")
    public ApiResponse<PageResponse<ContractLiquidationResponse>> getLiquidationHistory(@PageableDefault(size = 20) Pageable pageable) {
        return ApiResponse.success(tenancyService.getLiquidationHistory(pageable));
    }
}

