package com.cmullen.triens.model;

import jakarta.persistence.*;

@Entity
public class BudgetBucket extends LedgerAccount {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_bucket_id")
    private BudgetBucket parentBucket;

    @Column(nullable = false)
    private int displayOrder;

    @Column(nullable = false)
    private boolean active;

    @Embedded
    private BudgetGoal goal;
}