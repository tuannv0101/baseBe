package com.company.base.controller;

import com.company.base.common.ApiResponse;
import com.company.base.entity.FileMetadata;
import com.company.base.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping("/upload")
    public ApiResponse<FileMetadata> upload(@RequestParam("file") MultipartFile file) {
        return ApiResponse.success(fileService.upload(file));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource> download(@PathVariable Long id) {
        Resource file = fileService.download(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + file.getFilename() + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(file);
    }
}
