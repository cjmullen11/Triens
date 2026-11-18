package com.cmullen.triens.model;

import java.math.BigDecimal;

import jakarta.persistence.*;

@Entity
public class SecurityEntry extends TransactionEntry {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "security_id", nullable = false)
    private Security security;

    @Column(nullable = false)
    private BigDecimal quantity;

    @Column(nullable = false)
    private BigDecimal executionPrice;
}