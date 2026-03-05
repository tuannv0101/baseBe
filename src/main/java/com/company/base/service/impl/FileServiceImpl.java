package com.company.base.service.impl;

import com.company.base.entity.FileMetadata;
import com.company.base.exception.AppException;
import com.company.base.repository.FileMetadataRepository;
import com.company.base.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
/**
 * Service implementation containing business logic for this module.
 */

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    private final FileMetadataRepository fileRepository;

    @Override
    public FileMetadata upload(MultipartFile file) {
        try {
            String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
            String fileName = UUID.randomUUID().toString() + "_" + originalFileName;
            Path targetLocation = Paths.get(uploadDir).toAbsolutePath().normalize().resolve(fileName);
            
            Files.createDirectories(targetLocation.getParent());
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            FileMetadata metadata = FileMetadata.builder()
                    .fileName(fileName)
                    .fileType(file.getContentType())
                    .fileSize(file.getSize())
                    .filePath(targetLocation.toString())
                    .build();

            return fileRepository.save(metadata);
        } catch (IOException ex) {
            throw new AppException(500, "Could not store file. Error: " + ex.getMessage());
        }
    }

    @Override
    public Resource download(Long fileId) {
        FileMetadata metadata = fileRepository.findById(fileId)
                .orElseThrow(() -> new AppException(404, "File not found"));
        try {
            Path filePath = Paths.get(metadata.getFilePath()).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) return resource;
            else throw new AppException(404, "File not found on disk");
        } catch (MalformedURLException ex) {
            throw new AppException(500, "File path is invalid");
        }
    }

    @Override
    public void delete(Long fileId) {
        FileMetadata metadata = fileRepository.findById(fileId)
                .orElseThrow(() -> new AppException(404, "File not found"));
        try {
            Files.deleteIfExists(Paths.get(metadata.getFilePath()));
            fileRepository.delete(metadata);
        } catch (IOException ex) {
            throw new AppException(500, "Could not delete file");
        }
    }
}
