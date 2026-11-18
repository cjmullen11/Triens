package com.cmullen.triens.model;


import java.time.Instant;
import java.util.Currency;
import java.util.UUID;

public abstract class LedgerAccount{
    private UUID id;
    private String name;
    private Currency currency;
    private Instant createdAt;
    private Instant updatedAt;
    private boolean active;
    private String description;
}