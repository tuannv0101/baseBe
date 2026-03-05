package com.company.base.controller.host;

import com.company.base.common.ApiResponse;
import com.company.base.dto.response.host.DebtReportResponse;
import com.company.base.dto.response.host.RevenueReportResponse;
import com.company.base.dto.response.host.VacancyRateReportResponse;
import com.company.base.service.ReportsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller that exposes API endpoints for this module.
 */

@RestController
@RequestMapping("/api/v1/host/reports")
@RequiredArgsConstructor
public class ReportsController {

    private final ReportsService reportsService;

    @GetMapping("/revenue")
    public ApiResponse<RevenueReportResponse> getRevenueReport(
            @RequestParam(required = false) Integer fromMonth,
            @RequestParam(required = false) Integer fromYear,
            @RequestParam(required = false) Integer toMonth,
            @RequestParam(required = false) Integer toYear
    ) {
        return ApiResponse.success(reportsService.getRevenueReport(fromMonth, fromYear, toMonth, toYear));
    }

    @GetMapping("/vacancy-rate")
    public ApiResponse<VacancyRateReportResponse> getVacancyRateReport(
            @RequestParam(required = false) Integer fromMonth,
            @RequestParam(required = false) Integer fromYear,
            @RequestParam(required = false) Integer toMonth,
            @RequestParam(required = false) Integer toYear
    ) {
        return ApiResponse.success(reportsService.getVacancyRateReport(fromMonth, fromYear, toMonth, toYear));
    }

    @GetMapping("/debt")
    public ApiResponse<DebtReportResponse> getDebtReport() {
        return ApiResponse.success(reportsService.getDebtReport());
    }
}
