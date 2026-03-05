package com.company.base.service;

import com.company.base.dto.response.DebtReportResponse;
import com.company.base.dto.response.RevenueReportResponse;
import com.company.base.dto.response.VacancyRateReportResponse;
/**
 * Service contract defining operations for this module.
 */

public interface ReportsService {
    RevenueReportResponse getRevenueReport(Integer fromMonth, Integer fromYear, Integer toMonth, Integer toYear);
    VacancyRateReportResponse getVacancyRateReport(Integer fromMonth, Integer fromYear, Integer toMonth, Integer toYear);
    DebtReportResponse getDebtReport();
}
