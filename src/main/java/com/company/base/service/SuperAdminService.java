package com.company.base.service;

import com.company.base.dto.request.admin.LandlordSubscriptionRequest;
import com.company.base.dto.request.admin.SubscriptionPlanRequest;
import com.company.base.dto.request.admin.SupportTicketRequest;
import com.company.base.dto.request.admin.SupportTicketUpdateRequest;
import com.company.base.dto.request.admin.SystemConfigRequest;
import com.company.base.dto.response.admin.LandlordProfileResponse;
import com.company.base.dto.response.admin.LandlordSubscriptionResponse;
import com.company.base.dto.response.admin.SubscriptionPlanResponse;
import com.company.base.dto.response.admin.SuperAdminDashboardResponse;
import com.company.base.dto.response.admin.SupportTicketResponse;
import com.company.base.dto.response.admin.SystemConfigResponse;

import java.util.List;

/**
 * Service nghiệp vụ Super Admin: duyệt chủ nhà, gói dịch vụ, cấu hình hệ thống, ticket hỗ trợ.
 */
public interface SuperAdminService {
    /**
     * Lấy dữ liệu dashboard quản trị theo khoảng thời gian (tháng/năm).
     */
    SuperAdminDashboardResponse getDashboard(Integer fromMonth, Integer fromYear, Integer toMonth, Integer toYear);

    /**
     * Lấy danh sách chủ nhà (có thể lọc theo trạng thái hồ sơ).
     */
    List<LandlordProfileResponse> getLandlords(String status);

    /**
     * Duyệt hồ sơ chủ nhà (chuyển sang trạng thái được phép sử dụng hệ thống).
     */
    LandlordProfileResponse approveLandlord(Long landlordProfileId);

    /**
     * Khóa chủ nhà (thường do vi phạm/đang điều tra), kèm ghi chú.
     */
    LandlordProfileResponse lockLandlord(Long landlordProfileId, String note);

    /**
     * Mở khóa chủ nhà.
     */
    LandlordProfileResponse unlockLandlord(Long landlordProfileId);

    /**
     * Tạo mới gói đăng ký (subscription plan).
     */
    SubscriptionPlanResponse createPlan(SubscriptionPlanRequest request);

    /**
     * Cập nhật gói đăng ký theo ID.
     */
    SubscriptionPlanResponse updatePlan(Long planId, SubscriptionPlanRequest request);

    /**
     * Lấy danh sách các gói đăng ký.
     */
    List<SubscriptionPlanResponse> getPlans();

    /**
     * Xóa gói đăng ký theo ID.
     */
    void deletePlan(Long planId);

    /**
     * Tạo đăng ký gói cho một chủ nhà.
     */
    LandlordSubscriptionResponse createSubscription(LandlordSubscriptionRequest request);

    /**
     * Lấy danh sách đăng ký gói theo chủ nhà (nếu truyền landlordProfileId).
     */
    List<LandlordSubscriptionResponse> getSubscriptions(Long landlordProfileId);

    /**
     * Tạo mới hoặc cập nhật (upsert) cấu hình hệ thống theo configKey.
     */
    SystemConfigResponse upsertSystemConfig(SystemConfigRequest request);

    /**
     * Lấy danh sách cấu hình hệ thống.
     */
    List<SystemConfigResponse> getSystemConfigs();

    /**
     * Tạo ticket hỗ trợ (ví dụ: phản ánh lỗi, yêu cầu hỗ trợ nghiệp vụ).
     */
    SupportTicketResponse createSupportTicket(SupportTicketRequest request);

    /**
     * Cập nhật ticket hỗ trợ (trạng thái, người xử lý, ghi chú...).
     */
    SupportTicketResponse updateSupportTicket(Long ticketId, SupportTicketUpdateRequest request);

    /**
     * Lấy danh sách ticket hỗ trợ (có thể lọc theo trạng thái).
     */
    List<SupportTicketResponse> getSupportTickets(String status);
}
