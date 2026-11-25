package com.cmullen.triens.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
public class InterestEvent extends ScheduledEvent {

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "account_id", nullable = false)
  private FinancialAccount account;

  public InterestEvent(
      String name, Recurrence recurrence, LocalDate nextRun, FinancialAccount account) {
    super(name, "Interest event for " + account.getName() + " account.", recurrence, nextRun);
    this.account = account;
  }

  public FinancialAccount getAccount() {
    return account;
  }
}
