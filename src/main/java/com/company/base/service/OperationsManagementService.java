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
