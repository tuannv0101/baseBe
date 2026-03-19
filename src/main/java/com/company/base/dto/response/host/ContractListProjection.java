package com.company.base.dto.response.host;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface ContractListProjection {
    String getId();

    String getRoomId();

    String getContractCode();

    String getRoomNumber();

    String getPropertyName();

    String getTenantId();

    String getTenantName();

    String getTenantIdCardNumber();

    LocalDate getStartDate();

    LocalDate getEndDate();

    BigDecimal getRentAmount();

    String getStatus();
}
