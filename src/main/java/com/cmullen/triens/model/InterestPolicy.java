package com.cmullen.triens.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class InterestPolicy {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, precision = 10, scale = 6)
    private BigDecimal annualRate;

    protected InterestPolicy() {
        // JPA
    }

    protected InterestPolicy(BigDecimal annualRate) {
        this.annualRate = annualRate;
    }

    public abstract BigDecimal calculateInterest(List<DailyBalance> dailyBalances);
}