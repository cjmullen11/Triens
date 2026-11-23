package com.cmullen.triens.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import java.math.BigDecimal;

@Entity
public class LedgerEntry extends TransactionEntry {

  @Column(nullable = false, precision = 19, scale = 2)
  private BigDecimal amount;

  public LedgerEntry(LedgerAccount account, BigDecimal amount) {
    super(account);
    this.amount = amount;
  }

  public LedgerEntry() {
    // JPA
  }

  public BigDecimal getAmount() {
    return amount;
  }
}