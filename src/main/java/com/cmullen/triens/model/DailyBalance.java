package com.cmullen.triens.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DailyBalance(
    LocalDate date,
    BigDecimal cashBalance,
    BigDecimal holdingsBalance,
    BigDecimal unsettledCashDelta,
    BigDecimal unsettledHoldingsDelta) {
  public BigDecimal totalValue() {
    return cashBalance.add(holdingsBalance);
  }
}