package com.cmullen.triens.model;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class BankAccount extends FinancialAccount {
    @Column(precision = 19, scale = 2)
    private BigDecimal overdraftLimit;

    @Override
    public BigDecimal calculateInterest(List<DailyBalance> balances) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'calculateInterest'");
    }
}