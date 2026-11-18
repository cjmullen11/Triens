package com.cmullen.triens.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "allocation_plan")
public class AllocationPlan {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column (nullable = false)
    private String name;

    @OneToMany(
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    @JoinColumn(name = "plan_id", nullable = false)
    @OrderBy("position ASC")
    private List<AllocationStep> steps = new ArrayList<>();

    @Column (nullable = false)
    private boolean active;

    @Column (nullable = false)
    private Instant createdAt;

    @Column (nullable = false)
    private Instant updatedAt;

    protected AllocationPlan() {
        // JPA
    }

    @PrePersist
    protected void onCreate() {
        createdAt = Instant.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now(); 
    } 


    public BigDecimal allocate(BigDecimal available, Map<BudgetBucket, BigDecimal> balances, Map<BudgetBucket, BigDecimal> goalProgress){
        //TODO
        return new BigDecimal(0);
    };
}
