package com.cmullen.triens.service;

import com.cmullen.triens.dto.CreateRecurringTransactionEventRequest;
import com.cmullen.triens.model.InterestEvent;
import com.cmullen.triens.model.RecurringTransactionEvent;
import com.cmullen.triens.model.ScheduledEvent;
import com.cmullen.triens.repository.ScheduledEventRepository;
import java.time.Instant;
import java.util.UUID;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ScheduledEventService {

  private final ScheduledEventRepository scheduledEventRepository;
  private final TransactionService transactionService;
  private final BalanceService balanceService;

  // Constructor injection
  public ScheduledEventService(
      ScheduledEventRepository scheduledEventRepository,
      TransactionService transactionService,
      BalanceService balanceService) {
    this.scheduledEventRepository = scheduledEventRepository;
    this.transactionService = transactionService;
    this.balanceService = balanceService;
  }

  // Event creation
  @Transactional
  public RecurringTransactionEvent createRecurringTransactionEvent(
      CreateRecurringTransactionEventRequest dto) {

    return new RecurringTransactionEvent(
        dto.description(), dto.type(), dto.entries(), dto.nextRun(), dto.recurrence(), dto.name());
  }

  @Transactional
  public InterestEvent createInterestEvent(/* parameters */ ) {
    return null;
  }

  // Event management
  @Transactional
  public void cancelScheduledEvent(UUID eventId) {}

  @Transactional
  public void updateScheduledEvent(UUID eventId /* parameters */) {}

  // Scheduled execution
  @Scheduled(fixedDelay = 60_000)
  public void processDueEvents() {}

  // Event dispatch
  private void executeEvent(ScheduledEvent event) {}

  // Event-specific execution
  private void executeRecurringTransactionEvent(RecurringTransactionEvent event) {}

  private void executeInterestEvent(InterestEvent event) {}

  // Scheduling utilities
  private Instant calculateNextRun(ScheduledEvent event) {
    return null;
  }
}