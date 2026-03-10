package com.company.base.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "operations_documents")
@Where(clause = "del_yn IS NULL OR del_yn <> 'Y'")
@Data
public class OperationsDocument extends BaseEntity {
    // ID duy nhất của tài liệu vận hành.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Tiêu đề tài liệu.
    private String title;

    // Loại tài liệu (ví dụ: nội quy, hướng dẫn, biểu mẫu...).
    private String documentType;

    // ID tệp đính kèm (tham chiếu file_metadata).
    private Long fileId;

    // Cờ bật/tắt hiển thị tài liệu.
    private Boolean active;

    // Ghi chú nội bộ.
    private String note;
}
