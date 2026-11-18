package com.cmullen.triens.model;

import java.math.BigDecimal;
import java.time.Instant;

import jakarta.persistence.*;

@Embeddable
public class BudgetGoal {

    @Enumerated(EnumType.STRING)
    @Column(name = "goal_type", nullable = false, updatable = false)
    private GoalType type;

    @Column(name = "goal_target_amount", nullable = false,
            precision = 19, scale = 2, updatable = false)
    private BigDecimal targetAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "goal_recurrence", nullable = false, updatable = false)
    private Recurrence recurrence;

    @Column(name = "goal_created_at", nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    protected BudgetGoal() {} //JPA

    public BudgetGoal(GoalType type, BigDecimal targetAmount, Recurrence recurrence) {
        this.type = type;
        this.targetAmount = targetAmount;
        this.recurrence = recurrence;
    }
}