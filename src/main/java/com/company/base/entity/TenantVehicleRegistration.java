package com.company.base.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Where;

import java.time.LocalDate;

@Entity
@Table(name = "tenant_vehicle_registrations")
@Where(clause = "del_yn IS NULL OR del_yn <> 'Y'")
@Data
public class TenantVehicleRegistration extends BaseEntity {
    // ID duy nhất của đăng ký phương tiện.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ID người thuê đăng ký phương tiện.
    private String tenantId;

    // ID phòng người thuê đang ở (liên quan đến đăng ký).
    private String roomId;

    // Loại phương tiện: MOTORBIKE, CAR...
    private String vehicleType;

    // Biển số xe.
    private String plateNumber;

    // Ngày đăng ký.
    private LocalDate registeredDate;

    // Trạng thái duyệt đăng ký: PENDING, APPROVED, REJECTED.
    private String status;

    // Ghi chú bổ sung.
    private String note;
}
