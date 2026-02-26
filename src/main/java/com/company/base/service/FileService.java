package com.company.base.service;

import com.company.base.entity.FileMetadata;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    FileMetadata upload(MultipartFile file);
    Resource download(Long fileId);
    void delete(Long fileId);
}
