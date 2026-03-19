package com.company.base.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
    private String fileName;

    private String fileType;

    private long fileSize;

    private String filePath;

    private String refId;

    @Override
    protected String getIdPrefix() {
        return "FMD";
    }
}
