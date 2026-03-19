package com.company.base.controller.host;

import com.company.base.common.ApiResponse;
import com.company.base.dto.request.host.ExpenseRecordRequest;
import com.company.base.dto.request.host.MaintenanceAssignmentRequest;
import com.company.base.dto.request.host.MaintenanceRequestCreateRequest;
import com.company.base.dto.request.host.OperationsDocumentRequest;
import com.company.base.dto.response.host.ExpenseRecordResponse;
import com.company.base.dto.response.host.MaintenanceRequestResponse;
import com.company.base.dto.response.host.OperationsDocumentResponse;
import com.company.base.service.OperationsManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import com.company.base.common.pagination.PageResponse;

/**
 * REST controller that exposes API endpoints for this module.
 */

@RestController
@RequestMapping("/api/v1/host/operations")
@RequiredArgsConstructor
public class OperationsManagementController {

    private final OperationsManagementService operationsManagementService;

    @PostMapping("/maintenance-requests")
    public ApiResponse<MaintenanceRequestResponse> createMaintenanceRequest(@RequestBody MaintenanceRequestCreateRequest request) {
        return ApiResponse.success(operationsManagementService.createMaintenanceRequest(request));
    }

    @PutMapping("/maintenance-requests/{id}/assign")
    public ApiResponse<MaintenanceRequestResponse> assignMaintenanceRequest(
            @PathVariable String id,
            @RequestBody MaintenanceAssignmentRequest request
    ) {
        return ApiResponse.success(operationsManagementService.assignMaintenanceRequest(id, request));
    }

    @GetMapping("/maintenance-requests/{id}")
    public ApiResponse<MaintenanceRequestResponse> getMaintenanceRequestById(@PathVariable String id) {
        return ApiResponse.success(operationsManagementService.getMaintenanceRequestById(id));
    }

    @GetMapping("/maintenance-requests")
    public ApiResponse<PageResponse<MaintenanceRequestResponse>> getMaintenanceRequests(
            @RequestParam(required = false) String status,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        return ApiResponse.success(operationsManagementService.getMaintenanceRequests(status, pageable));
    }

    @PostMapping("/expenses")
    public ApiResponse<ExpenseRecordResponse> createExpenseRecord(@RequestBody ExpenseRecordRequest request) {
        return ApiResponse.success(operationsManagementService.createExpenseRecord(request));
    }

    @PutMapping("/expenses/{id}")
    public ApiResponse<ExpenseRecordResponse> updateExpenseRecord(@PathVariable String id, @RequestBody ExpenseRecordRequest request) {
        return ApiResponse.success(operationsManagementService.updateExpenseRecord(id, request));
    }

    @GetMapping("/expenses/{id}")
    public ApiResponse<ExpenseRecordResponse> getExpenseRecordById(@PathVariable String id) {
        return ApiResponse.success(operationsManagementService.getExpenseRecordById(id));
    }

    @GetMapping("/expenses")
    public ApiResponse<PageResponse<ExpenseRecordResponse>> getExpenseRecords(
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer year,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        return ApiResponse.success(operationsManagementService.getExpenseRecords(month, year, pageable));
    }

    @PutMapping("/expenses/{id}/delete")
    public ApiResponse<Void> deleteExpenseRecord(@PathVariable String id) {
        operationsManagementService.deleteExpenseRecord(id);
        return ApiResponse.success(null);
    }

    @PostMapping("/documents")
    public ApiResponse<OperationsDocumentResponse> createDocument(@RequestBody OperationsDocumentRequest request) {
        return ApiResponse.success(operationsManagementService.createDocument(request));
    }

    @PutMapping("/documents/{id}")
    public ApiResponse<OperationsDocumentResponse> updateDocument(@PathVariable String id, @RequestBody OperationsDocumentRequest request) {
        return ApiResponse.success(operationsManagementService.updateDocument(id, request));
    }

    @GetMapping("/documents/{id}")
    public ApiResponse<OperationsDocumentResponse> getDocumentById(@PathVariable String id) {
        return ApiResponse.success(operationsManagementService.getDocumentById(id));
    }

    @GetMapping("/documents")
    public ApiResponse<PageResponse<OperationsDocumentResponse>> getDocuments(
            @RequestParam(required = false) String documentType,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        return ApiResponse.success(operationsManagementService.getDocuments(documentType, pageable));
    }

    @PutMapping("/documents/{id}/delete")
    public ApiResponse<Void> deleteDocument(@PathVariable String id) {
        operationsManagementService.deleteDocument(id);
        return ApiResponse.success(null);
    }
}

