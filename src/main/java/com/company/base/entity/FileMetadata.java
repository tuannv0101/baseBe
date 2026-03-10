package com.company.base.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "file_metadata")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileMetadata extends BaseEntity {
    // ID duy nhất của tệp.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Tên hiển thị của tệp.
    private String fileName;

    // Loại/MIME type của tệp.
    private String fileType;

    // Kích thước tệp (byte).
    private long fileSize;

    // Đường dẫn lưu trữ tệp trên hệ thống.
    private String filePath;
}
