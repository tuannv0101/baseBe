package com.company.base.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "operations_documents")
@Where(clause = "del_yn IS NULL OR del_yn <> 'Y'")
@Data
public class OperationsDocument extends BaseEntity {
    // ID duy nhất của tài liệu vận hành.
// Tiêu đề tài liệu.
    private String title;

    // Loại tài liệu (ví dụ: nội quy, hướng dẫn, biểu mẫu...).
    private String documentType;

    // ID tệp đính kèm (tham chiếu file_metadata).
    private String fileId;

    // Cờ bật/tắt hiển thị tài liệu.
    private Boolean active;

    // Ghi chú nội bộ.
    private String note;

    @Override
    protected String getIdPrefix() {
        return "OPD";
    }
}

