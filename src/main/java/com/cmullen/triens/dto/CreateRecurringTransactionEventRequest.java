package com.cmullen.triens.dto;

import com.cmullen.triens.model.Recurrence;
import com.cmullen.triens.model.TransactionType;
import java.time.LocalDate;
import java.util.List;

public record CreateRecurringTransactionEventRequest(
    String description,
    TransactionType type,
    List<CreateTransactionEntryRequest> entries,
    LocalDate nextRun,
    Recurrence recurrence,
    String name) {}
