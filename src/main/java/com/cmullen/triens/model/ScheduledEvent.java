package com.cmullen.triens.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.time.Instant;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class ScheduledEvent {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(nullable = false)
  private String name;

  private Instant nextRun;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Recurrence recurrence;

  @Column(nullable = false)
  private boolean active;

  @Column(nullable = false, updatable = false)
  private Instant createdAt;

  @Column(nullable = false)
  private Instant updatedAt;

  // last run of this scheduled event (creating a PROPOSED transaction),
  // not the last time the transaction actually occured.
  private Instant lastRun;

  private Instant cancelledAt;

  @PrePersist
  protected void onCreate() {
    createdAt = Instant.now();
    updatedAt = createdAt;
  }

  @PreUpdate
  protected void onUpdate() {
    updatedAt = Instant.now();
  }

  protected ScheduledEvent() {
    // JPA
  }

  public boolean isDue(Instant now) {
    // TODO
    return false;
  }

  // To be called when run at dueDate
  private Instant calculateNextRun() {
    return null;
  }

  // the last time this scheduled event ran, not necessarily that its corresponding transaction
  // occured.
  public Instant getLastRun() {
    return lastRun;
  }
}
