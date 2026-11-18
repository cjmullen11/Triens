package com.cmullen.triens.model;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class Transaction {
    private UUID id;
    private String description;
    private TransactionType type;
    private TransactionStatus status;
    private LocalDate effectiveDate;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant cancelledAt;
    private List<TransactionEntry> entries;
    private ScheduledEvent source;
    private LocalDate scheduledDate;

    public boolean isPartiallySettled() {
        //TODO 
        return false;
    }

    public boolean isFullySettled() {
        //TODO
        return false;
    }

    public boolean canCancel() {
        //TODO
        return false;
    }
}
