package com.company.base.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "file_metadata")
@Where(clause = "del_yn IS NULL OR del_yn <> 'Y'")
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
