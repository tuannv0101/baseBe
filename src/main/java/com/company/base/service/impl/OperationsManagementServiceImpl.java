package com.company.base.service.impl;

import com.company.base.common.pagination.PageResponse;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public PageResponse<MaintenanceRequestResponse> getMaintenanceRequests(String status, Pageable pageable) {
        Page<MaintenanceRequestResponse> page = (status == null || status.isBlank())
                ? maintenanceRequestRepository.findAllByOrderByRequestedAtDescIdDesc(pageable).map(this::toMaintenanceResponse)
                : maintenanceRequestRepository.findByStatusIgnoreCaseOrderByRequestedAtDescIdDesc(status, pageable).map(this::toMaintenanceResponse);
        return PageResponse.of(page);
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
    public PageResponse<ExpenseRecordResponse> getExpenseRecords(Integer month, Integer year, Pageable pageable) {
        Page<ExpenseRecordResponse> page;
        if (month == null || year == null) {
            page = expenseRecordRepository.findAllByOrderByExpenseDateDescIdDesc(pageable).map(this::toExpenseResponse);
        } else {
            YearMonth ym = YearMonth.of(year, month);
            LocalDate fromDate = ym.atDay(1);
            LocalDate toDate = ym.atEndOfMonth();
            page = expenseRecordRepository.findByExpenseDateBetweenOrderByExpenseDateDescIdDesc(fromDate, toDate, pageable)
                    .map(this::toExpenseResponse);
        }
        return PageResponse.of(page);
    }

    @Override
    public void deleteExpenseRecord(Long id) {
        ExpenseRecord entity = getExpenseEntity(id);
        entity.setDelYn("Y");
        expenseRecordRepository.save(entity);
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
    public PageResponse<OperationsDocumentResponse> getDocuments(String documentType, Pageable pageable) {
        Page<OperationsDocumentResponse> page = (documentType == null || documentType.isBlank())
                ? operationsDocumentRepository.findAllByOrderByDocumentTypeAscTitleAsc(pageable).map(this::toDocumentResponse)
                : operationsDocumentRepository.findByDocumentTypeIgnoreCaseOrderByTitleAsc(documentType, pageable).map(this::toDocumentResponse);
        return PageResponse.of(page);
    }

    @Override
    public void deleteDocument(Long id) {
        OperationsDocument entity = getDocumentEntity(id);
        entity.setDelYn("Y");
        operationsDocumentRepository.save(entity);
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
                .fileUrl(entity.getFileId() != null ? "/api/v1/host/files/" + entity.getFileId() : null)
                .active(entity.getActive())
                .note(entity.getNote())
                .build();
    }
}
