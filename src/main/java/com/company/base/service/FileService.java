package com.company.base.service;

import com.company.base.entity.FileMetadata;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

/**
 * Service quản lý tệp: upload, download, xóa.
 */
public interface FileService {
    /**
     * Upload tệp và lưu metadata (tên, loại, kích thước, đường dẫn...).
     */
    FileMetadata upload(MultipartFile file);

    /**
     * Tải xuống tệp theo ID metadata.
     */
    Resource download(Long fileId);

    /**
     * Xóa metadata và/hoặc tệp vật lý theo ID.
     */
    void delete(Long fileId);
}
