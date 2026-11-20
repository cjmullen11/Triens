package com.cmullen.triens.model;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class TransactionEntry {

  @Id @GeneratedValue(strategy = GenerationType.UUID) private UUID id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "account_id", nullable = false)
  private LedgerAccount account;

  private Instant settledAt;

  public boolean isSettled() { return settledAt != null; }

  public TransactionEntry(LedgerAccount account) { this.account = account; }
}