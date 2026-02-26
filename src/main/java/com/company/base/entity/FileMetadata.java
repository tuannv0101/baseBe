package com.company.base.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name = "file_metadata")
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class FileMetadata extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fileName;
    private String fileType;
    private long fileSize;
    private String filePath;
}
