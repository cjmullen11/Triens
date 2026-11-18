package com.cmullen.triens.model;

import java.time.Instant;
import java.util.UUID;

public abstract class TransactionEntry {
    private UUID id;
    private Instant settledAt;

    public boolean isSettled() {
        return settledAt != null;
    }
}