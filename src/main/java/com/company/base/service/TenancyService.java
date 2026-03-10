package com.company.base.service;

import com.company.base.dto.request.host.ContractLiquidationRequest;
import com.company.base.dto.request.host.ContractRequest;
import com.company.base.dto.request.host.TenantRequest;
import com.company.base.dto.response.host.ContractLiquidationResponse;
import com.company.base.dto.response.host.ContractResponse;
import com.company.base.dto.response.host.TenantResponse;

import java.util.List;

/**
 * Service quản lý người thuê và hợp đồng thuê (tenancy).
 */
public interface TenancyService {
    /**
     * Tạo mới thông tin người thuê.
     */
    TenantResponse createTenant(TenantRequest request);

    /**
     * Cập nhật thông tin người thuê theo ID.
     */
    TenantResponse updateTenant(Long id, TenantRequest request);

    /**
     * Lấy chi tiết người thuê theo ID.
     */
    TenantResponse getTenantById(Long id);

    /**
     * Lấy danh sách toàn bộ người thuê.
     */
    List<TenantResponse> getAllTenants();

    /**
     * Lấy danh sách người thuê theo trạng thái khai báo tạm trú.
     */
    List<TenantResponse> getTenantsByTemporaryResidenceStatus(boolean declared);

    /**
     * Xóa người thuê theo ID.
     */
    void deleteTenant(Long id);

    /**
     * Tạo mới hợp đồng thuê.
     */
    ContractResponse createContract(ContractRequest request);

    /**
     * Cập nhật hợp đồng thuê theo ID.
     */
    ContractResponse updateContract(Long id, ContractRequest request);

    /**
     * Lấy chi tiết hợp đồng thuê theo ID.
     */
    ContractResponse getContractById(Long id);

    /**
     * Lấy danh sách toàn bộ hợp đồng.
     */
    List<ContractResponse> getAllContracts();

    /**
     * Lấy danh sách hợp đồng đang hiệu lực (effective).
     */
    List<ContractResponse> getEffectiveContracts();

    /**
     * Lấy danh sách hợp đồng đang chờ xử lý/duyệt (pending).
     */
    List<ContractResponse> getPendingContracts();

    /**
     * Xóa hợp đồng theo ID.
     */
    void deleteContract(Long id);

    /**
     * Thanh lý hợp đồng và tạo lịch sử thanh lý.
     */
    ContractLiquidationResponse liquidateContract(Long contractId, ContractLiquidationRequest request);

    /**
     * Lấy danh sách lịch sử thanh lý hợp đồng.
     */
    List<ContractLiquidationResponse> getLiquidationHistory();
}
