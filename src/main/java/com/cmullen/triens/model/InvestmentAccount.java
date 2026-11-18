package com.cmullen.triens.model;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.Entity;

@Entity
public class InvestmentAccount extends FinancialAccount {

    @Override
    public BigDecimal calculateInterest(List<DailyBalance> balances) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'calculateInterest'");
    }
}