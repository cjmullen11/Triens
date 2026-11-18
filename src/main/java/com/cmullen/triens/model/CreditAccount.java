package com.cmullen.triens.model;

import java.math.BigDecimal;
import java.util.List;

public class CreditAccount extends FinancialAccount {
    private BigDecimal limit;
    private BankAccount defaultPaymentAccount;
    private int StatementClosingDate;
    private int StatementDueDate;

    public BigDecimal getAvailableCredit(BigDecimal balance){
        return limit.subtract(balance);
    };

    public BigDecimal getUtilization(BigDecimal balance) {
        return balance.divide(limit);
    }

    @Override
    public BigDecimal calculateInterest(List<DailyBalance> balances) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'calculateInterest'");
    }
}