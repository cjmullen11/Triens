package com.cmullen.triens.model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapKeyJoinColumn;

@Entity 
public class WeightedSplitStep extends AllocationStep {
    @ElementCollection
    @CollectionTable(
        name = "weighted_split_step_weights",
        joinColumns = @JoinColumn(name = "step_id")
    )
    @MapKeyJoinColumn(name = "bucket_id")
    @Column(name = "weight", nullable = false, precision = 18, scale = 8)
    private Map<BudgetBucket, BigDecimal> weights = new HashMap<>();

    @Override
    public Map<BudgetBucket, BigDecimal> allocate(BigDecimal available, Map<BudgetBucket, BigDecimal> balances,
            Map<BudgetBucket, BigDecimal> goalProgress) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'allocate'");
    }
    
}