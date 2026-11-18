package com.cmullen.triens.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class LedgerEntry extends TransactionEntry {

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;
}