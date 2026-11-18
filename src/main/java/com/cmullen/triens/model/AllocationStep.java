package com.cmullen.triens.model;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class AllocationStep {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column (nullable = false)
    private int position;

    @Column (nullable = false)
    private boolean active;
    
    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy ("position ASC")
    public abstract Map<BudgetBucket, BigDecimal> allocate(BigDecimal available, Map<BudgetBucket, BigDecimal> balances, Map<BudgetBucket, BigDecimal> goalProgress);
}
