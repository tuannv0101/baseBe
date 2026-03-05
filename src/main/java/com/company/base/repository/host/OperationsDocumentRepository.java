package com.company.base.repository.host;

import com.company.base.entity.OperationsDocument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository for data access operations.
 */

public interface OperationsDocumentRepository extends JpaRepository<OperationsDocument, Long> {
    List<OperationsDocument> findAllByOrderByDocumentTypeAscTitleAsc();

    List<OperationsDocument> findByDocumentTypeIgnoreCaseOrderByTitleAsc(String documentType);

    List<OperationsDocument> findByDocumentTypeIgnoreCaseAndActiveTrueOrderByTitleAsc(String documentType);
}
