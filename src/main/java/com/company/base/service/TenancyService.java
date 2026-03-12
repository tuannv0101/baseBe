package com.company.base.service;

import com.company.base.dto.request.host.ContractLiquidationRequest;
import com.company.base.dto.request.host.ContractRequest;
import com.company.base.dto.request.host.TenantRequest;
import com.company.base.dto.response.host.ContractLiquidationResponse;
import com.company.base.dto.response.host.ContractResponse;
import com.company.base.dto.response.host.TenantResponse;

import com.company.base.common.pagination.PageResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

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

    TenantResponse getTenantByIdCardNumber(String idCardNumber);

    /**
     * Lấy danh sách toàn bộ người thuê.
     */
    PageResponse<TenantResponse> getAllTenants(Pageable pageable);

    /**
     * Lấy danh sách người thuê theo trạng thái khai báo tạm trú.
     */
    PageResponse<TenantResponse> getTenantsByTemporaryResidenceStatus(boolean declared, Pageable pageable);

    /**
     * Xóa người thuê theo ID.
     */
    void deleteTenant(Long id);

    /**
     * Upload ảnh (hoặc file) cho tenant và lưu metadata vào bảng file_metadata.
     * Tenant hiện tham chiếu file qua portraitImageId.
     */
    TenantResponse uploadTenantPortrait(Long tenantId, MultipartFile file);

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
    PageResponse<ContractResponse> getAllContracts(Pageable pageable);

    /**
     * Lấy danh sách hợp đồng đang hiệu lực (effective).
     */
    PageResponse<ContractResponse> getEffectiveContracts(Pageable pageable);

    /**
     * Lấy danh sách hợp đồng đang chờ xử lý/duyệt (pending).
     */
    PageResponse<ContractResponse> getPendingContracts(Pageable pageable);

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
    PageResponse<ContractLiquidationResponse> getLiquidationHistory(Pageable pageable);
}
