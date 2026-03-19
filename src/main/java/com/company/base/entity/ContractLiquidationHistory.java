package com.company.base.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.Where;

import java.time.LocalDate;

@Entity
@Table(name = "contract_liquidation_histories")
@Where(clause = "del_yn IS NULL OR del_yn <> 'Y'")
@Data
public class ContractLiquidationHistory extends BaseEntity {
    // Unique liquidation history ID.
// Linked contract ID.
    private String contractId;

    // Related room ID.
    private String roomId;

    // Related tenant ID.
    private String tenantId;

    // Liquidation date.
    private LocalDate liquidationDate;

    // Liquidation reason.
    private String reason;

    // Additional note.
    private String note;

    @Override
    protected String getIdPrefix() {
        return "CLH";
    }
}


