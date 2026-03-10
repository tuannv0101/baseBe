package com.company.base.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Where;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "contracts")
@Where(clause = "del_yn IS NULL OR del_yn <> 'Y'")
@Data
public class Contract extends BaseEntity {
    // ID duy nhất của hợp đồng.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ID phòng được thuê.
    private String roomId;

    // ID người thuê trong hợp đồng.
    private String tenantId;

    // Ngày bắt đầu hiệu lực hợp đồng.
    private LocalDate startDate;

    // Ngày kết thúc hợp đồng.
    private LocalDate endDate;

    // Tiền đặt cọc.
    private BigDecimal depositAmount;

    // Giá thuê thực tế áp dụng.
    private BigDecimal actualRent;

    // Trạng thái hợp đồng: ACTIVE, EXPIRED, TERMINATED.
    private String status; // ACTIVE, EXPIRED, TERMINATED
}
