package com.cmullen.triens.model;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity
public class CompoundInterestPolicy extends InterestPolicy {

    @Enumerated(EnumType.STRING)
    @Column(name = "compounding_recurrence", nullable = false)
    private Recurrence compoundingRecurrence;

    @Enumerated(EnumType.STRING)
    @Column(name = "posting_recurrence", nullable = false)
    private Recurrence postingRecurrence;

    @Override
    public BigDecimal calculateInterest(List<DailyBalance> dailyBalances) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'calculateInterest'");
    }
}