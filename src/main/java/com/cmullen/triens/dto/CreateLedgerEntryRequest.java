package com.cmullen.triens.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateLedgerEntryRequest(UUID accountId, BigDecimal amount)
    implements CreateTransactionEntryRequest {}
