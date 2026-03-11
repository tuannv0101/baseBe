package com.company.base.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Where;

import java.time.LocalDate;

@Entity
@Table(name = "contract_liquidation_histories")
@Where(clause = "del_yn IS NULL OR del_yn <> 'Y'")
@Data
public class ContractLiquidationHistory extends BaseEntity {
    // Unique liquidation history ID.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Linked contract ID.
    private Long contractId;

    // Related room ID.
    private Long roomId;

    // Related tenant ID.
    private Long tenantId;

    // Liquidation date.
    private LocalDate liquidationDate;

    // Liquidation reason.
    private String reason;

    // Additional note.
    private String note;
}
