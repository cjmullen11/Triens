package com.cmullen.triens.service;

import com.cmullen.triens.dto.CreateLedgerEntryRequest;
import com.cmullen.triens.dto.CreateSecurityEntryRequest;
import com.cmullen.triens.dto.CreateTransactionEntryRequest;
import com.cmullen.triens.dto.CreateTransactionRequest;
import com.cmullen.triens.model.LedgerAccount;
import com.cmullen.triens.model.LedgerEntry;
import com.cmullen.triens.model.ScheduledEvent;
import com.cmullen.triens.model.Security;
import com.cmullen.triens.model.SecurityEntry;
import com.cmullen.triens.model.Transaction;
import com.cmullen.triens.model.TransactionEntry;
import com.cmullen.triens.model.TransactionStatus;
import com.cmullen.triens.model.TransactionType;
import com.cmullen.triens.repository.LedgerAccountRepository;
import com.cmullen.triens.repository.SecurityRepository;
import com.cmullen.triens.repository.TransactionEntryRepository;
import com.cmullen.triens.repository.TransactionRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionService {

  private final TransactionRepository transactionRepository;
  private final LedgerAccountRepository accountRepository;
  private final SecurityRepository securityRepository;
  private final TransactionEntryRepository transactionEntryRepository;

  public TransactionService(
      TransactionRepository transactionRepository,
      LedgerAccountRepository accountRepository,
      SecurityRepository securityRepository,
      TransactionEntryRepository transactionEntryRepository) {

    this.transactionRepository = transactionRepository;
    this.accountRepository = accountRepository;
    this.securityRepository = securityRepository;
    this.transactionEntryRepository = transactionEntryRepository;
  }

  @Transactional
  private Transaction
  createTransaction(String description, TransactionType type,
                    TransactionStatus status, LocalDate effectiveDate,
                    List<TransactionEntry> entries, ScheduledEvent source) {
    Transaction transaction = new Transaction(description, type, status,
                                              effectiveDate, entries, source);
    return transactionRepository.save(transaction);
  }

  // Create and persist a new MANUAL transaction
  // status starts as initiated. (see other constructors for scheduled)
  @Transactional
  public Transaction createUserTransaction(CreateTransactionRequest request) {
    List<TransactionEntry> entries = new ArrayList<>();
    for (CreateTransactionEntryRequest entry : request.entries()) {
      entries.add(processTransactionEntryRequest(entry));
    }
    return createTransaction(request.description(), request.type(),
                             TransactionStatus.INITIATED,
                             request.effectiveDate(), entries, null);
  }

  // Create and persist a new scheduled transaction
  // status starts as proposed.
  @Transactional
  public Transaction
  proposeTransaction(String description, TransactionType type,
                     LocalDate effectiveDate, List<TransactionEntry> entries,
                     ScheduledEvent source) {
    return createTransaction(description, type, TransactionStatus.PROPOSED,
                             effectiveDate, entries, source);
  }

  private TransactionEntry
  processTransactionEntryRequest(CreateTransactionEntryRequest request) {
    LedgerAccount account =
        accountRepository.findById(request.accountId()).orElseThrow();
    if (request instanceof CreateLedgerEntryRequest) {
      CreateLedgerEntryRequest ledgerRequest =
          ((CreateLedgerEntryRequest)request);
      return new LedgerEntry(account, ledgerRequest.amount());
    } else if (request instanceof CreateSecurityEntryRequest) {
      CreateSecurityEntryRequest securityRequest =
          ((CreateSecurityEntryRequest)request);
      Security security =
          securityRepository.findById(securityRequest.securityId())
              .orElseThrow();
      return new SecurityEntry(account, security, securityRequest.quantity(),
                               securityRequest.executionPrice());
    } else {
      throw new IllegalArgumentException();
    }
  }

  // Transition a proposed transaction to initiated.
  @Transactional
  public Transaction initiateTransaction(UUID transactionId) {
    Transaction transaction =
        transactionRepository.findById(transactionId).orElseThrow();

    transaction.initiate();

    return transaction;
  }

  // Cancel a transaction if its current state permits cancellation.
  @Transactional
  public Transaction cancelTransaction(UUID transactionId) {
    Transaction transaction =
        transactionRepository.findById(transactionId).orElseThrow();

    transaction.cancel();

    return transaction;
  }

  // Mark an individual transaction entry as settled.
  @Transactional
  public TransactionEntry settleEntry(UUID entryId) {
    TransactionEntry entry =
        transactionEntryRepository.findById(entryId).orElseThrow();
    entry.settle();
    return entry;
  }
}