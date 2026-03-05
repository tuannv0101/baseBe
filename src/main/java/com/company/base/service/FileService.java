package com.company.base.service;

import com.company.base.entity.FileMetadata;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
/**
 * Service contract defining operations for this module.
 */

public interface FileService {
    FileMetadata upload(MultipartFile file);
    Resource download(Long fileId);
    void delete(Long fileId);
}
