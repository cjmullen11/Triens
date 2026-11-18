package com.cmullen.triens.model;

import java.math.BigDecimal;
import java.util.List;

public class BankAccount extends FinancialAccount {
    private BigDecimal overdraftLimit;

    @Override
    public BigDecimal calculateInterest(List<DailyBalance> balances) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'calculateInterest'");
    }
}