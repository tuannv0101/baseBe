package com.company.base.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Entity
@Table(name = "maintenance_requests")
@Where(clause = "del_yn IS NULL OR del_yn <> 'Y'")
@Data
public class MaintenanceRequest extends BaseEntity {
    // ID duy nhất của yêu cầu bảo trì/sửa chữa.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ID phòng phát sinh yêu cầu.
    private Long roomId;

    // ID người thuê tạo yêu cầu.
    private Long tenantId;

    // Tiêu đề yêu cầu.
    private String title;

    // Mô tả chi tiết vấn đề cần xử lý.
    private String description;

    // Mức ưu tiên (ví dụ: LOW/MEDIUM/HIGH).
    private String priority;

    // Trạng thái xử lý yêu cầu (ví dụ: OPEN/IN_PROGRESS/COMPLETED/CANCELLED).
    private String status;

    // Kỹ thuật viên/nhân sự phụ trách (tên hoặc định danh).
    private String assignedTechnician;

    // ID tệp đính kèm (tham chiếu file_metadata).
    private Long attachmentFileId;

    // Thời điểm ghi nhận yêu cầu.
    private LocalDateTime requestedAt;

    // Thời điểm hoàn tất xử lý.
    private LocalDateTime completedAt;

    // Ghi chú bổ sung (nội bộ hoặc phản hồi cho người thuê).
    private String note;
}
