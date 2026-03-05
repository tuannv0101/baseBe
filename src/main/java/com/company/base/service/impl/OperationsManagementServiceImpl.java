package com.company.base.service.impl;

import com.company.base.dto.request.host.ExpenseRecordRequest;
import com.company.base.dto.request.host.MaintenanceAssignmentRequest;
import com.company.base.dto.request.host.MaintenanceRequestCreateRequest;
import com.company.base.dto.request.host.OperationsDocumentRequest;
import com.company.base.dto.response.host.ExpenseRecordResponse;
import com.company.base.dto.response.host.MaintenanceRequestResponse;
import com.company.base.dto.response.host.OperationsDocumentResponse;
import com.company.base.entity.ExpenseRecord;
import com.company.base.entity.MaintenanceRequest;
import com.company.base.entity.OperationsDocument;
import com.company.base.exception.AppException;
import com.company.base.repository.host.ExpenseRecordRepository;
import com.company.base.repository.host.FileMetadataRepository;
import com.company.base.repository.host.MaintenanceRequestRepository;
import com.company.base.repository.host.OperationsDocumentRepository;
import com.company.base.service.OperationsManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

/**
 * Service implementation containing business logic for this module.
 */

@Service
@RequiredArgsConstructor
public class OperationsManagementServiceImpl implements OperationsManagementService {

    private final MaintenanceRequestRepository maintenanceRequestRepository;
    private final ExpenseRecordRepository expenseRecordRepository;
    private final OperationsDocumentRepository operationsDocumentRepository;
    private final FileMetadataRepository fileMetadataRepository;

    @Override
    public MaintenanceRequestResponse createMaintenanceRequest(MaintenanceRequestCreateRequest request) {
        MaintenanceRequest entity = new MaintenanceRequest();
        entity.setRoomId(request.getRoomId());
        entity.setTenantId(request.getTenantId());
        entity.setTitle(request.getTitle());
        entity.setDescription(request.getDescription());
        entity.setPriority(request.getPriority());
        entity.setStatus("RECEIVED");
        entity.setRequestedAt(LocalDateTime.now());
        return toMaintenanceResponse(maintenanceRequestRepository.save(entity));
    }

    @Override
    public MaintenanceRequestResponse assignMaintenanceRequest(Long id, MaintenanceAssignmentRequest request) {
        MaintenanceRequest entity = getMaintenanceEntity(id);
        entity.setAssignedTechnician(request.getAssignedTechnician());
        entity.setNote(request.getNote());
        if (request.getStatus() != null && !request.getStatus().isBlank()) {
            String status = request.getStatus().trim().toUpperCase();
            entity.setStatus(status);
            if ("COMPLETED".equals(status)) {
                entity.setCompletedAt(LocalDateTime.now());
            }
        }
        return toMaintenanceResponse(maintenanceRequestRepository.save(entity));
    }

    @Override
    public MaintenanceRequestResponse getMaintenanceRequestById(Long id) {
        return toMaintenanceResponse(getMaintenanceEntity(id));
    }

    @Override
    public List<MaintenanceRequestResponse> getMaintenanceRequests(String status) {
        if (status == null || status.isBlank()) {
            return maintenanceRequestRepository.findAllByOrderByRequestedAtDescIdDesc()
                    .stream()
                    .map(this::toMaintenanceResponse)
                    .toList();
        }
        return maintenanceRequestRepository.findByStatusIgnoreCaseOrderByRequestedAtDescIdDesc(status)
                .stream()
                .map(this::toMaintenanceResponse)
                .toList();
    }

    @Override
    public ExpenseRecordResponse createExpenseRecord(ExpenseRecordRequest request) {
        ExpenseRecord entity = new ExpenseRecord();
        applyExpenseUpdate(entity, request);
        return toExpenseResponse(expenseRecordRepository.save(entity));
    }

    @Override
    public ExpenseRecordResponse updateExpenseRecord(Long id, ExpenseRecordRequest request) {
        ExpenseRecord entity = getExpenseEntity(id);
        applyExpenseUpdate(entity, request);
        return toExpenseResponse(expenseRecordRepository.save(entity));
    }

    @Override
    public ExpenseRecordResponse getExpenseRecordById(Long id) {
        return toExpenseResponse(getExpenseEntity(id));
    }

    @Override
    public List<ExpenseRecordResponse> getExpenseRecords(Integer month, Integer year) {
        if (month == null || year == null) {
            return expenseRecordRepository.findAllByOrderByExpenseDateDescIdDesc()
                    .stream()
                    .map(this::toExpenseResponse)
                    .toList();
        }
        YearMonth ym = YearMonth.of(year, month);
        LocalDate fromDate = ym.atDay(1);
        LocalDate toDate = ym.atEndOfMonth();
        return expenseRecordRepository.findByExpenseDateBetweenOrderByExpenseDateDescIdDesc(fromDate, toDate)
                .stream()
                .map(this::toExpenseResponse)
                .toList();
    }

    @Override
    public void deleteExpenseRecord(Long id) {
        if (!expenseRecordRepository.existsById(id)) {
            throw new AppException(HttpStatus.NOT_FOUND.value(), "Expense record not found");
        }
        expenseRecordRepository.deleteById(id);
    }

    @Override
    public OperationsDocumentResponse createDocument(OperationsDocumentRequest request) {
        validateFileId(request.getFileId());
        OperationsDocument entity = new OperationsDocument();
        applyDocumentUpdate(entity, request);
        return toDocumentResponse(operationsDocumentRepository.save(entity));
    }

    @Override
    public OperationsDocumentResponse updateDocument(Long id, OperationsDocumentRequest request) {
        validateFileId(request.getFileId());
        OperationsDocument entity = getDocumentEntity(id);
        applyDocumentUpdate(entity, request);
        return toDocumentResponse(operationsDocumentRepository.save(entity));
    }

    @Override
    public OperationsDocumentResponse getDocumentById(Long id) {
        return toDocumentResponse(getDocumentEntity(id));
    }

    @Override
    public List<OperationsDocumentResponse> getDocuments(String documentType) {
        if (documentType == null || documentType.isBlank()) {
            return operationsDocumentRepository.findAllByOrderByDocumentTypeAscTitleAsc()
                    .stream()
                    .map(this::toDocumentResponse)
                    .toList();
        }
        return operationsDocumentRepository.findByDocumentTypeIgnoreCaseOrderByTitleAsc(documentType)
                .stream()
                .map(this::toDocumentResponse)
                .toList();
    }

    @Override
    public void deleteDocument(Long id) {
        if (!operationsDocumentRepository.existsById(id)) {
            throw new AppException(HttpStatus.NOT_FOUND.value(), "Document not found");
        }
        operationsDocumentRepository.deleteById(id);
    }

    private void applyExpenseUpdate(ExpenseRecord entity, ExpenseRecordRequest request) {
        entity.setExpenseDate(request.getExpenseDate() != null ? request.getExpenseDate() : LocalDate.now());
        entity.setCategory(request.getCategory());
        entity.setAmount(request.getAmount());
        entity.setDescription(request.getDescription());
        entity.setVendor(request.getVendor());
        entity.setPaidBy(request.getPaidBy());
        entity.setNote(request.getNote());
    }

    private void applyDocumentUpdate(OperationsDocument entity, OperationsDocumentRequest request) {
        entity.setTitle(request.getTitle());
        entity.setDocumentType(normalizeText(request.getDocumentType()));
        entity.setFileId(request.getFileId());
        entity.setActive(request.getActive() == null ? Boolean.TRUE : request.getActive());
        entity.setNote(request.getNote());
    }

    private void validateFileId(Long fileId) {
        if (fileId == null || !fileMetadataRepository.existsById(fileId)) {
            throw new AppException(HttpStatus.NOT_FOUND.value(), "File not found");
        }
    }

    private String normalizeText(String value) {
        return value == null ? null : value.trim().toUpperCase();
    }

    private MaintenanceRequest getMaintenanceEntity(Long id) {
        return maintenanceRequestRepository.findById(id)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND.value(), "Maintenance request not found"));
    }

    private ExpenseRecord getExpenseEntity(Long id) {
        return expenseRecordRepository.findById(id)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND.value(), "Expense record not found"));
    }

    private OperationsDocument getDocumentEntity(Long id) {
        return operationsDocumentRepository.findById(id)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND.value(), "Document not found"));
    }

    private MaintenanceRequestResponse toMaintenanceResponse(MaintenanceRequest entity) {
        return MaintenanceRequestResponse.builder()
                .id(entity.getId())
                .roomId(entity.getRoomId())
                .tenantId(entity.getTenantId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .priority(entity.getPriority())
                .status(entity.getStatus())
                .assignedTechnician(entity.getAssignedTechnician())
                .requestedAt(entity.getRequestedAt())
                .completedAt(entity.getCompletedAt())
                .note(entity.getNote())
                .build();
    }

    private ExpenseRecordResponse toExpenseResponse(ExpenseRecord entity) {
        return ExpenseRecordResponse.builder()
                .id(entity.getId())
                .expenseDate(entity.getExpenseDate())
                .category(entity.getCategory())
                .amount(entity.getAmount())
                .description(entity.getDescription())
                .vendor(entity.getVendor())
                .paidBy(entity.getPaidBy())
                .note(entity.getNote())
                .build();
    }

    private OperationsDocumentResponse toDocumentResponse(OperationsDocument entity) {
        return OperationsDocumentResponse.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .documentType(entity.getDocumentType())
                .fileId(entity.getFileId())
                .fileUrl(entity.getFileId() != null ? "/api/v1/files/" + entity.getFileId() : null)
                .active(entity.getActive())
                .note(entity.getNote())
                .build();
    }
}
