package com.company.base.service;

import com.company.base.dto.request.host.ExpenseRecordRequest;
import com.company.base.dto.request.host.MaintenanceAssignmentRequest;
import com.company.base.dto.request.host.MaintenanceRequestCreateRequest;
import com.company.base.dto.request.host.OperationsDocumentRequest;
import com.company.base.dto.response.host.ExpenseRecordResponse;
import com.company.base.dto.response.host.MaintenanceRequestResponse;
import com.company.base.dto.response.host.OperationsDocumentResponse;

import java.util.List;

/**
 * Service quản lý vận hành: yêu cầu bảo trì, chi phí, tài liệu vận hành.
 */
public interface OperationsManagementService {
    /**
     * Tạo mới yêu cầu bảo trì/sửa chữa.
     */
    MaintenanceRequestResponse createMaintenanceRequest(MaintenanceRequestCreateRequest request);

    /**
     * Phân công yêu cầu bảo trì cho kỹ thuật viên/nhân sự xử lý.
     */
    MaintenanceRequestResponse assignMaintenanceRequest(Long id, MaintenanceAssignmentRequest request);

    /**
     * Lấy chi tiết yêu cầu bảo trì theo ID.
     */
    MaintenanceRequestResponse getMaintenanceRequestById(Long id);

    /**
     * Lấy danh sách yêu cầu bảo trì (có thể lọc theo trạng thái).
     */
    List<MaintenanceRequestResponse> getMaintenanceRequests(String status);

    /**
     * Tạo mới bản ghi chi phí vận hành.
     */
    ExpenseRecordResponse createExpenseRecord(ExpenseRecordRequest request);

    /**
     * Cập nhật bản ghi chi phí theo ID.
     */
    ExpenseRecordResponse updateExpenseRecord(Long id, ExpenseRecordRequest request);

    /**
     * Lấy chi tiết bản ghi chi phí theo ID.
     */
    ExpenseRecordResponse getExpenseRecordById(Long id);

    /**
     * Lấy danh sách chi phí theo tháng/năm.
     */
    List<ExpenseRecordResponse> getExpenseRecords(Integer month, Integer year);

    /**
     * Xóa bản ghi chi phí theo ID.
     */
    void deleteExpenseRecord(Long id);

    /**
     * Tạo mới tài liệu vận hành.
     */
    OperationsDocumentResponse createDocument(OperationsDocumentRequest request);

    /**
     * Cập nhật tài liệu vận hành theo ID.
     */
    OperationsDocumentResponse updateDocument(Long id, OperationsDocumentRequest request);

    /**
     * Lấy chi tiết tài liệu vận hành theo ID.
     */
    OperationsDocumentResponse getDocumentById(Long id);

    /**
     * Lấy danh sách tài liệu vận hành (có thể lọc theo loại tài liệu).
     */
    List<OperationsDocumentResponse> getDocuments(String documentType);

    /**
     * Xóa tài liệu vận hành theo ID.
     */
    void deleteDocument(Long id);
}
