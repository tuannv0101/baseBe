package com.company.base.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "support_tickets")
@Where(clause = "del_yn IS NULL OR del_yn <> 'Y'")
@Data
public class SupportTicket extends BaseEntity {
    // ID duy nhất của ticket hỗ trợ.
// ID hồ sơ chủ nhà gửi yêu cầu hỗ trợ.
    private String landlordProfileId;

    // Tiêu đề yêu cầu hỗ trợ.
    private String title;

    // Nội dung/mô tả chi tiết vấn đề.
    private String description;

    // Trạng thái ticket: OPEN, IN_PROGRESS, RESOLVED, CLOSED.
    private String status;

    // Mức ưu tiên: LOW, MEDIUM, HIGH.
    private String priority;

    // Người được phân công xử lý (tên hoặc định danh).
    private String assignedTo;

    // Ghi chú kết quả/hướng xử lý sau khi giải quyết.
    private String resolutionNote;

    @Override
    protected String getIdPrefix() {
        return "STK";
    }
}


