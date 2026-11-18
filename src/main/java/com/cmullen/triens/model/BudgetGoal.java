package com.cmullen.triens.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class BudgetGoal {
    private UUID id;
    private GoalType type;
    private BigDecimal targetAmount;
    private Recurrence  recurrence;
    private boolean active;
    private Instant createdAt;
    private Instant updatedAt;
}
