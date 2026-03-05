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
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
            @PathVariable Long id,
            @RequestBody MaintenanceAssignmentRequest request
    ) {
        return ApiResponse.success(operationsManagementService.assignMaintenanceRequest(id, request));
    }

    @GetMapping("/maintenance-requests/{id}")
    public ApiResponse<MaintenanceRequestResponse> getMaintenanceRequestById(@PathVariable Long id) {
        return ApiResponse.success(operationsManagementService.getMaintenanceRequestById(id));
    }

    @GetMapping("/maintenance-requests")
    public ApiResponse<List<MaintenanceRequestResponse>> getMaintenanceRequests(@RequestParam(required = false) String status) {
        return ApiResponse.success(operationsManagementService.getMaintenanceRequests(status));
    }

    @PostMapping("/expenses")
    public ApiResponse<ExpenseRecordResponse> createExpenseRecord(@RequestBody ExpenseRecordRequest request) {
        return ApiResponse.success(operationsManagementService.createExpenseRecord(request));
    }

    @PutMapping("/expenses/{id}")
    public ApiResponse<ExpenseRecordResponse> updateExpenseRecord(@PathVariable Long id, @RequestBody ExpenseRecordRequest request) {
        return ApiResponse.success(operationsManagementService.updateExpenseRecord(id, request));
    }

    @GetMapping("/expenses/{id}")
    public ApiResponse<ExpenseRecordResponse> getExpenseRecordById(@PathVariable Long id) {
        return ApiResponse.success(operationsManagementService.getExpenseRecordById(id));
    }

    @GetMapping("/expenses")
    public ApiResponse<List<ExpenseRecordResponse>> getExpenseRecords(
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer year
    ) {
        return ApiResponse.success(operationsManagementService.getExpenseRecords(month, year));
    }

    @DeleteMapping("/expenses/{id}")
    public ApiResponse<Void> deleteExpenseRecord(@PathVariable Long id) {
        operationsManagementService.deleteExpenseRecord(id);
        return ApiResponse.success(null);
    }

    @PostMapping("/documents")
    public ApiResponse<OperationsDocumentResponse> createDocument(@RequestBody OperationsDocumentRequest request) {
        return ApiResponse.success(operationsManagementService.createDocument(request));
    }

    @PutMapping("/documents/{id}")
    public ApiResponse<OperationsDocumentResponse> updateDocument(@PathVariable Long id, @RequestBody OperationsDocumentRequest request) {
        return ApiResponse.success(operationsManagementService.updateDocument(id, request));
    }

    @GetMapping("/documents/{id}")
    public ApiResponse<OperationsDocumentResponse> getDocumentById(@PathVariable Long id) {
        return ApiResponse.success(operationsManagementService.getDocumentById(id));
    }

    @GetMapping("/documents")
    public ApiResponse<List<OperationsDocumentResponse>> getDocuments(@RequestParam(required = false) String documentType) {
        return ApiResponse.success(operationsManagementService.getDocuments(documentType));
    }

    @DeleteMapping("/documents/{id}")
    public ApiResponse<Void> deleteDocument(@PathVariable Long id) {
        operationsManagementService.deleteDocument(id);
        return ApiResponse.success(null);
    }
}
