package com.cmullen.triens.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Transaction {
  @Id @GeneratedValue(strategy = GenerationType.UUID) private UUID id;

  private String description;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private TransactionType type;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private TransactionStatus status;

  // finanical date of transaction, aka business day it is
  private LocalDate effectiveDate;

  private Instant createdAt;

  private Instant updatedAt;

  // When the transaction begins to take effect
  private Instant initiatedAt;

  // time of cancellation.
  // only able to cancel if not initiated.
  private Instant cancelledAt;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL,
             orphanRemoval = true)
  @JoinColumn(name = "transaction_id", nullable = false)
  private List<TransactionEntry> entries = new ArrayList<>();

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "source_id")
  private ScheduledEvent source;

  @PrePersist
  protected void onCreate() {
    createdAt = Instant.now();
    updatedAt = createdAt;
  }

  @PreUpdate
  protected void onUpdate() {
    updatedAt = Instant.now();
  }

  protected Transaction() {
    // JPA
  }

  public Transaction(String description, TransactionType type,
                     TransactionStatus status, LocalDate effectiveDate,
                     List<TransactionEntry> entries, ScheduledEvent source) {
    this.description = description;
    this.type = type;
    this.status = status;
    this.effectiveDate = effectiveDate;
    this.entries = entries;
    this.source = source;
  }

  public boolean isPartiallySettled() {
    boolean hasSettled = false;
    boolean hasUnsettled = false;

    for (TransactionEntry entry : entries) {
      if (entry.isSettled()) {
        hasSettled = true;
      } else {
        hasUnsettled = true;
      }

      if (hasSettled && hasUnsettled) {
        return true;
      }
    }

    return false;
  }

  public boolean isFullySettled() {
    for (TransactionEntry entry : entries) {
      if (!entry.isSettled()) {
        return false;
      }
    }

    return true;
  }

  public boolean canCancel() { return status == TransactionStatus.PROPOSED; }

  public boolean canInitiate() { return status == TransactionStatus.PROPOSED; }

  public void initiate() {
    if (!canInitiate()) {
      throw new IllegalStateException("Transaction is already: " +
                                      status.toString());
    }
    status = TransactionStatus.INITIATED;
    initiatedAt = Instant.now();
  }

  public void cancel() {
    if (!canCancel()) {
      throw new IllegalStateException("Transaction is already: " +
                                      status.toString());
    }
    status = TransactionStatus.CANCELLED;
    cancelledAt = Instant.now();
  }
}
