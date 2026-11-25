package com.cmullen.triens.service;

import com.cmullen.triens.dto.CreateLedgerEntryRequest;
import com.cmullen.triens.dto.CreateRecurringTransactionEventRequest;
import com.cmullen.triens.dto.CreateSecurityEntryRequest;
import com.cmullen.triens.dto.CreateTransactionEntryRequest;
import com.cmullen.triens.model.FinancialAccount;
import com.cmullen.triens.model.InterestEvent;
import com.cmullen.triens.model.LedgerAccount;
import com.cmullen.triens.model.LedgerEntry;
import com.cmullen.triens.model.Recurrence;
import com.cmullen.triens.model.RecurringTransactionEvent;
import com.cmullen.triens.model.ScheduledEvent;
import com.cmullen.triens.model.Security;
import com.cmullen.triens.model.SecurityEntry;
import com.cmullen.triens.model.TransactionEntry;
import com.cmullen.triens.repository.LedgerAccountRepository;
import com.cmullen.triens.repository.ScheduledEventRepository;
import com.cmullen.triens.repository.SecurityRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ScheduledEventService {

  private final ScheduledEventRepository scheduledEventRepository;
  private final TransactionService transactionService;
  private final BalanceService balanceService;
  private final LedgerAccountRepository ledgerAccountRepository;
  private final SecurityRepository securityRepository;

  // Constructor injection
  public ScheduledEventService(
      ScheduledEventRepository scheduledEventRepository,
      TransactionService transactionService,
      BalanceService balanceService,
      LedgerAccountRepository ledgerAccountRepository,
      SecurityRepository securityRepository) {
    this.scheduledEventRepository = scheduledEventRepository;
    this.transactionService = transactionService;
    this.balanceService = balanceService;
    this.ledgerAccountRepository = ledgerAccountRepository;
    this.securityRepository = securityRepository;
  }

  // Event creation
  @Transactional
  public RecurringTransactionEvent createRecurringTransactionEvent(
      CreateRecurringTransactionEventRequest dto) {

    return new RecurringTransactionEvent(
        dto.description(), dto.type(), dto.entries(), dto.nextRun(), dto.recurrence(), dto.name());
  }

  @Transactional
  public InterestEvent createInterestEvent(
      String name, Recurrence recurrence, LocalDate nextRun, FinancialAccount account) {
    return new InterestEvent(name, recurrence, nextRun, account);
  }

  // Event management
  @Transactional
  public void cancelScheduledEvent(UUID eventId) {
    ScheduledEvent event = scheduledEventRepository.findById(eventId).orElseThrow();
    event.cancel();
  }

  @Transactional
  public void updateScheduledEvent() {}

  // Scheduled execution
  @Scheduled(cron = "0 0 3 * * *")
  @Transactional
  public void processDueEvents() {
    List<ScheduledEvent> dueEvents =
        scheduledEventRepository.findByActiveTrueAndNextRunLessThan(LocalDate.now());

    for (ScheduledEvent event : dueEvents) {
      executeEvent(event);
    }
  }

  // Event dispatch
  private void executeEvent(ScheduledEvent event) {
    if (event instanceof RecurringTransactionEvent) {
      executeRecurringTransactionEvent((RecurringTransactionEvent) event);
    } else if (event instanceof InterestEvent) {
      executeInterestEvent((InterestEvent) event);
    }
    updateNextRun(event);
  }

  private List<TransactionEntry> resolveTransactionEntries(
      List<CreateTransactionEntryRequest> entries) {
    List<TransactionEntry> transactionEntires = new ArrayList<>();
    for (CreateTransactionEntryRequest req : entries) {

      if (req instanceof CreateSecurityEntryRequest) {
        CreateSecurityEntryRequest securityEntryRequest = (CreateSecurityEntryRequest) req;
        LedgerAccount account =
            ledgerAccountRepository.findById(securityEntryRequest.accountId()).orElseThrow();
        Security security =
            securityRepository.findById(securityEntryRequest.securityId()).orElseThrow();
        SecurityEntry securityEntry =
            new SecurityEntry(
                account,
                security,
                securityEntryRequest.quantity(),
                securityEntryRequest.executionPrice());
        transactionEntires.add(securityEntry);
      } else if (req instanceof CreateTransactionEntryRequest) {
        CreateLedgerEntryRequest ledgerEntryRequest = (CreateLedgerEntryRequest) req;
        LedgerAccount account =
            ledgerAccountRepository.findById(ledgerEntryRequest.accountId()).orElseThrow();
        LedgerEntry ledgerEntry = new LedgerEntry(account, ledgerEntryRequest.amount());
        transactionEntires.add(ledgerEntry);
      }
    }

    return transactionEntires;
  }

  // Event-specific execution
  private void executeRecurringTransactionEvent(RecurringTransactionEvent event) {
    List<CreateTransactionEntryRequest> entires = event.getEntryRequests();
    List<TransactionEntry> transactionEntries = resolveTransactionEntries(entires);

    transactionService.proposeTransaction(
        event.getDescription(), event.getType(), event.getNextRun(), transactionEntries, event);
  }

  private void executeInterestEvent(InterestEvent event) {}

  // Scheduling utilities
  private void updateNextRun(ScheduledEvent event) {
    LocalDate last = event.getNextRun();
    switch (event.getRecurrence()) {
      case BIWEEKLY:
        event.setNextRun(last.plusWeeks(14));
        break;
      case DAILY:
        event.setNextRun(last.plusDays(1));
        break;
      case LAST_OF_MONTH:
        last.plusMonths(1);
        last.plusDays(last.lengthOfMonth() - last.getDayOfMonth());
        event.setNextRun(last);
        break;
      case MONTHLY:
        last.plusMonths(1);
        event.setNextRun(last);
        break;
      case ONCE:
        event.setNextRun(null);
        break;
      case SECOND_LAST_OF_MONTH:
        last.plusMonths(1);
        last.plusDays(last.lengthOfMonth() - last.getDayOfMonth() - 2);
        event.setNextRun(last);
        break;
      case THIRD_LAST_OF_MONTH:
        last.plusMonths(1);
        last.plusDays(last.lengthOfMonth() - last.getDayOfMonth() - 3);
        event.setNextRun(last);
        break;
      case WEEKLY:
        last.plusWeeks(1);
        event.setNextRun(last);
        break;
      case YEARLY:
        last.plusYears(1);
        event.setNextRun(last);
        break;
      default:
        break;
    }
  }
}