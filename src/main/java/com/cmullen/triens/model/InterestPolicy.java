package com.cmullen.triens.model;

import java.math.BigDecimal;

public abstract class InterestPolicy {
    private UUID id;
    private BigDecimal annualRate;

    public abstract BigDecimal calculateInterest(List<DailyBalance> dailyBalances);
}
