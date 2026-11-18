package com.cmullen.triens.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmullen.triens.dto.CreateLedgerEntryRequest;
import com.cmullen.triens.dto.CreateSecurityEntryRequest;
import com.cmullen.triens.dto.CreateTransactionEntryRequest;
import com.cmullen.triens.dto.CreateTransactionRequest;
import com.cmullen.triens.model.LedgerAccount;
import com.cmullen.triens.model.LedgerEntry;
import com.cmullen.triens.model.Security;
import com.cmullen.triens.model.SecurityEntry;
import com.cmullen.triens.model.Transaction;
import com.cmullen.triens.model.TransactionEntry;
import com.cmullen.triens.repository.LedgerAccountRepository;
import com.cmullen.triens.repository.SecurityRepository;
import com.cmullen.triens.repository.TransactionRepository;

@Service
public class TransactionService {


    private final TransactionRepository transactionRepository;
    private final LedgerAccountRepository accountRepository;
    private final SecurityRepository securityRepository;

    public TransactionService(
            TransactionRepository transactionRepository,
            LedgerAccountRepository accountRepository,
            SecurityRepository securityRepository) {

        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.securityRepository = securityRepository;
    }

    // Create and persist a new MANUAL transaction
    // status starts as initiated. (see other constructors for scheduled)
    @Transactional
    public Transaction createTransaction(CreateTransactionRequest request) {
        List<TransactionEntry> entries = new ArrayList<>();
        for(CreateTransactionEntryRequest entry : request.entries()) {
            entries.add(processTransactionEntryRequest(entry));
        }
        Transaction transaction = new Transaction(request.description(), request.type(), request.effectiveDate(), entries);

        return transactionRepository.save(transaction);


    }

    private TransactionEntry processTransactionEntryRequest(CreateTransactionEntryRequest request) {
        LedgerAccount account = accountRepository.getReferenceById(request.accountId());
        if(request instanceof CreateLedgerEntryRequest) {
            CreateLedgerEntryRequest ledgerRequest = ((CreateLedgerEntryRequest)request);
            return new LedgerEntry(account, ledgerRequest.amount());
        } else if(request instanceof CreateSecurityEntryRequest) {
            CreateSecurityEntryRequest securityRequest =((CreateSecurityEntryRequest)request);
            Security security = securityRepository.getReferenceById(securityRequest.securityId());
            return new SecurityEntry(account, security, securityRequest.quantity(), securityRequest.executionPrice());
        } else {
            throw new IllegalArgumentException();
        }
    }

    // Transition a proposed transaction to initiated.
    @Transactional
    public Transaction initiateTransaction(UUID transactionId) {
        // TODO
        throw new UnsupportedOperationException("Unimplemented method 'initiateTransaction'");
    }

    // Cancel a transaction if its current state permits cancellation.
    @Transactional
    public Transaction cancelTransaction(UUID transactionId) {
        // TODO
        throw new UnsupportedOperationException("Unimplemented method 'cancelTransaction'");
    }

    // Mark an individual transaction entry as settled.
    @Transactional
    public Transaction settleEntry(UUID transactionId, UUID entryId, Instant settledAt) {
        // TODO
        throw new UnsupportedOperationException("Unimplemented method 'settleEntry'");
    }

    // Retrieve a transaction by ID.
    @Transactional(readOnly = true)
    public Transaction getTransaction(UUID transactionId) {
        // TODO
        throw new UnsupportedOperationException("Unimplemented method 'getTransaction'");
    }
}