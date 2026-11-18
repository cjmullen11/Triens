package com.cmullen.triens.model;

import java.math.BigDecimal;
import java.time.Instant;

public class SecurityEntry {
    private LedgerAccount account;
    private Security security;
    private BigDecimal quantity;
    private Instant createdAt;
    private Instant updatedAt;
}
