package com.cmullen.triens.model;

import com.cmullen.triens.dto.CreateTransactionEntryRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.time.LocalDate;
import java.util.List;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
public class RecurringTransactionEvent extends ScheduledEvent {
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private TransactionType type;

  @JdbcTypeCode(SqlTypes.JSON)
  @Column(columnDefinition = "jsonb", nullable = false)
  private List<CreateTransactionEntryRequest> entryTemplates;

  public RecurringTransactionEvent(
      String description,
      TransactionType type,
      List<CreateTransactionEntryRequest> entries,
      LocalDate nextRun,
      Recurrence recurrence,
      String name) {
    super(name, description, recurrence, nextRun);
    this.type = type;
    entryTemplates = entries;
  }

  public TransactionType getType() {
    return type;
  }

  public List<CreateTransactionEntryRequest> getEntryRequests() {
    return entryTemplates;
  }
}
