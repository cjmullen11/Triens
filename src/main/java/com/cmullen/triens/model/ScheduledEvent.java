package com.cmullen.triens.model;

import java.time.Instant;
import java.util.UUID;

public abstract class ScheduledEvent {
    private UUID id;
    private String name;
    private Instant nextRun;
    private Recurrence recurrence;
    private boolean active;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant lastRun;
    private Instant cancelledAt;

    public boolean isDue(Instant now) {
        //TODO
        return false;
    }

    // To be called when run at dueDate
    private Instant calculateNextRun() {
        return null;
    }
}
