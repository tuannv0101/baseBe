package com.company.base.service;

import com.company.base.dto.response.host.DebtReportResponse;
import com.company.base.dto.response.host.RevenueReportResponse;
import com.company.base.dto.response.host.VacancyRateReportResponse;

/**
 * Service báo cáo: doanh thu, tỷ lệ trống, công nợ.
 */
public interface ReportsService {
    /**
     * Báo cáo doanh thu theo khoảng thời gian (tháng/năm).
     */
    RevenueReportResponse getRevenueReport(Integer fromMonth, Integer fromYear, Integer toMonth, Integer toYear);

    /**
     * Báo cáo tỷ lệ phòng trống theo khoảng thời gian (tháng/năm).
     */
    VacancyRateReportResponse getVacancyRateReport(Integer fromMonth, Integer fromYear, Integer toMonth, Integer toYear);

    /**
     * Báo cáo công nợ hiện tại (các khoản chưa thanh toán/quá hạn).
     */
    DebtReportResponse getDebtReport();
}
