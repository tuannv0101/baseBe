package com.company.base.service;

import com.company.base.dto.request.ExpenseRecordRequest;
import com.company.base.dto.request.MaintenanceAssignmentRequest;
import com.company.base.dto.request.MaintenanceRequestCreateRequest;
import com.company.base.dto.request.OperationsDocumentRequest;
import com.company.base.dto.response.ExpenseRecordResponse;
import com.company.base.dto.response.MaintenanceRequestResponse;
import com.company.base.dto.response.OperationsDocumentResponse;

import java.util.List;
/**
 * Service contract defining operations for this module.
 */

public interface OperationsManagementService {
    MaintenanceRequestResponse createMaintenanceRequest(MaintenanceRequestCreateRequest request);
    MaintenanceRequestResponse assignMaintenanceRequest(Long id, MaintenanceAssignmentRequest request);
    MaintenanceRequestResponse getMaintenanceRequestById(Long id);
    List<MaintenanceRequestResponse> getMaintenanceRequests(String status);

    ExpenseRecordResponse createExpenseRecord(ExpenseRecordRequest request);
    ExpenseRecordResponse updateExpenseRecord(Long id, ExpenseRecordRequest request);
    ExpenseRecordResponse getExpenseRecordById(Long id);
    List<ExpenseRecordResponse> getExpenseRecords(Integer month, Integer year);
    void deleteExpenseRecord(Long id);

    OperationsDocumentResponse createDocument(OperationsDocumentRequest request);
    OperationsDocumentResponse updateDocument(Long id, OperationsDocumentRequest request);
    OperationsDocumentResponse getDocumentById(Long id);
    List<OperationsDocumentResponse> getDocuments(String documentType);
    void deleteDocument(Long id);
}
