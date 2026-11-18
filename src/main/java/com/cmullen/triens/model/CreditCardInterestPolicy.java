package com.cmullen.triens.model;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity 
public class CreditCardInterestPolicy extends InterestPolicy{
    @Column (nullable = false)
    private int gracePeriodDays;

    @Override
    public BigDecimal calculateInterest(List<DailyBalance> dailyBalances) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'calculateInterest'");
    }
}
