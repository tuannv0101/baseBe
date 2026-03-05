package com.company.base.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "contract_liquidation_histories")
@Data
public class ContractLiquidationHistory extends BaseEntity {
    // Unique liquidation history ID.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // Linked contract ID.
    private Long contractId;
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
}
