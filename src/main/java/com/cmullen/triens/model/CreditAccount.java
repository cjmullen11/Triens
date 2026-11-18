package com.cmullen.triens.model;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity 
public class CreditAccount extends FinancialAccount {
    
    @Column (nullable = false)
    private BigDecimal creditLimit;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "default_payment_account_id")
    private BankAccount defaultPaymentAccount;

    @Column(nullable = false)
    private int statementClosingDay;

    @Column(nullable = false)
    private int statementDueDay;

    public BigDecimal getAvailableCredit(BigDecimal balance) {
        return creditLimit.add(balance);
    }

    public BigDecimal getUtilization(BigDecimal balance) {
        return balance.negate().divide(creditLimit, MathContext.DECIMAL64);
    }

    @Override
    public BigDecimal calculateInterest(List<DailyBalance> balances) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'calculateInterest'");
    }
}