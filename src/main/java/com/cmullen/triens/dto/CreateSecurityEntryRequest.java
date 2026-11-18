package com.cmullen.triens.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateSecurityEntryRequest(
    UUID accountId,
    UUID securityId,
    BigDecimal quantity,
    BigDecimal executionPrice
) implements CreateTransactionEntryRequest {}