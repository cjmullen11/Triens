package com.cmullen.triens.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class SecurityEntry extends TransactionEntry {

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "security_id", nullable = false)
  private Security security;

  @Column(nullable = false, precision = 28, scale = 10)
  private BigDecimal quantity;

  @Column(nullable = false, precision = 28, scale = 10)
  private BigDecimal executionPrice;

  public SecurityEntry(
      LedgerAccount account, Security security, BigDecimal quantity, BigDecimal executionPrice) {
    super(account);
    this.security = security;
    this.quantity = quantity;
    this.executionPrice = executionPrice;
  }

  public SecurityEntry() {
    // JPA
  }

  public Security getSecurity() {
    return security;
  }

  public BigDecimal getQuantity() {
    return quantity;
  }
}