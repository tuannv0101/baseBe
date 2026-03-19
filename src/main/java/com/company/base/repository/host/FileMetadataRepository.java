package com.company.base.repository.host;

import com.company.base.entity.FileMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for data access operations.
 */

public interface FileMetadataRepository extends JpaRepository<FileMetadata, String> {
}

