package com.company.base.service;

import com.company.base.entity.FileMetadata;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {
    FileMetadata upload(MultipartFile file);

    FileMetadata upload(MultipartFile file, String refId);

    List<FileMetadata> upload(List<MultipartFile> files, String refId);

    Resource download(String fileId);

    void delete(String fileId);
}
