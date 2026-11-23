package com.cmullen.triens.model;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class TransactionEntry {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "account_id", nullable = false)
  private LedgerAccount account;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "transaction_id", nullable = false)
  private Transaction transaction;

  private Instant settledAt;
  private Instant createdAt;
  private Instant updatedAt;

  @PrePersist
  protected void onCreate() {
    createdAt = Instant.now();
    updatedAt = createdAt;
  }

  @PreUpdate
  protected void onUpdate() {
    updatedAt = Instant.now();
  }

  public TransactionEntry() {
    // JPA
  }

  public TransactionEntry(LedgerAccount account) {
    this.account = account;
  }

  public boolean isSettled() {
    return settledAt != null;
  }

  public void settle() {
    settledAt = Instant.now();
  }

  public LedgerAccount getAccount() {
    return account;
  }

  public UUID getUUID() {
    return id;
  }

  public Instant getSettlementInstant() {
    return settledAt;
  }

  public Transaction getTransaction() {
    return transaction;
  }

  void setTransaction(Transaction transaction) {
    this.transaction = transaction;
  }
}