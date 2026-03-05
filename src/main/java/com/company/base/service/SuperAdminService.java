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
 * Service contract defining operations for this module.
 */
public interface SuperAdminService {
    SuperAdminDashboardResponse getDashboard(Integer fromMonth, Integer fromYear, Integer toMonth, Integer toYear);

    List<LandlordProfileResponse> getLandlords(String status);
    LandlordProfileResponse approveLandlord(Long landlordProfileId);
    LandlordProfileResponse lockLandlord(Long landlordProfileId, String note);
    LandlordProfileResponse unlockLandlord(Long landlordProfileId);

    SubscriptionPlanResponse createPlan(SubscriptionPlanRequest request);
    SubscriptionPlanResponse updatePlan(Long planId, SubscriptionPlanRequest request);
    List<SubscriptionPlanResponse> getPlans();
    void deletePlan(Long planId);

    LandlordSubscriptionResponse createSubscription(LandlordSubscriptionRequest request);
    List<LandlordSubscriptionResponse> getSubscriptions(Long landlordProfileId);

    SystemConfigResponse upsertSystemConfig(SystemConfigRequest request);
    List<SystemConfigResponse> getSystemConfigs();

    SupportTicketResponse createSupportTicket(SupportTicketRequest request);
    SupportTicketResponse updateSupportTicket(Long ticketId, SupportTicketUpdateRequest request);
    List<SupportTicketResponse> getSupportTickets(String status);
}
