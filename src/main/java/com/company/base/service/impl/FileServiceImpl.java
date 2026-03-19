package com.company.base.service.impl;

import com.company.base.entity.FileMetadata;
import com.company.base.exception.AppException;
import com.company.base.repository.host.FileMetadataRepository;
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
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    private final FileMetadataRepository fileRepository;

    @Override
    public FileMetadata upload(MultipartFile file) {
        return upload(file, null);
    }

    @Override
    public FileMetadata upload(MultipartFile file, String refId) {
        if (file == null || file.isEmpty()) {
            throw new AppException(400, "File is required");
        }

        try {
            String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
            String fileName = UUID.randomUUID() + "_" + originalFileName;
            Path targetLocation = Paths.get(uploadDir).toAbsolutePath().normalize().resolve(fileName);

            Files.createDirectories(targetLocation.getParent());
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            FileMetadata metadata = FileMetadata.builder()
                    .fileName(fileName)
                    .fileType(file.getContentType())
                    .fileSize(file.getSize())
                    .filePath(targetLocation.toString())
                    .refId(StringUtils.hasText(refId) ? refId.trim() : null)
                    .build();

            return fileRepository.save(metadata);
        } catch (IOException ex) {
            throw new AppException(500, "Could not store file. Error: " + ex.getMessage());
        }
    }

    @Override
    public List<FileMetadata> upload(List<MultipartFile> files, String refId) {
        if (files == null || files.isEmpty()) {
            throw new AppException(400, "At least one file is required");
        }

        return files.stream()
                .filter(file -> file != null && !file.isEmpty())
                .map(file -> upload(file, refId))
                .toList();
    }

    @Override
    public Resource download(String fileId) {
        FileMetadata metadata = fileRepository.findById(fileId)
                .orElseThrow(() -> new AppException(404, "File not found"));
        try {
            Path filePath = Paths.get(metadata.getFilePath()).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            }
            throw new AppException(404, "File not found on disk");
        } catch (MalformedURLException ex) {
            throw new AppException(500, "File path is invalid");
        }
    }

    @Override
    public void delete(String fileId) {
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
