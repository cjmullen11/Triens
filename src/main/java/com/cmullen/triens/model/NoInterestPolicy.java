package com.cmullen.triens.model;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.Entity;

@Entity
public class NoInterestPolicy extends InterestPolicy {

    public NoInterestPolicy() {
        super(BigDecimal.ZERO);
    }

    @Override
    public BigDecimal calculateInterest(List<DailyBalance> dailyBalances) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'calculateInterest'");
    }
}
