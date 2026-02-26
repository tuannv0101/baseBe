package com.company.base.repository;

import com.company.base.entity.FileMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileMetadataRepository extends JpaRepository<FileMetadata, Long> {
}
