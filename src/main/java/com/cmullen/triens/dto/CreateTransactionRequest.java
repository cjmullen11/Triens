package com.cmullen.triens.dto;

import java.time.LocalDate;
import java.util.List;

import com.cmullen.triens.model.TransactionType;

public record CreateTransactionRequest (
    String description,
    TransactionType type,
    LocalDate effectiveDate,
    List<CreateTransactionEntryRequest> entries
){}
