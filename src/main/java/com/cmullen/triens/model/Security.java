package com.cmullen.triens.model;

import java.time.Instant;
import java.util.UUID;

public class Security {
    private UUID id;
    private String ticker;
    private String name;
    private boolean active;
    private Instant createdAt;
    private Instant updatedAt;
}
