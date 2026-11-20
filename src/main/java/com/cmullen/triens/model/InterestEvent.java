package com.cmullen.triens.model;

import java.time.YearMonth;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity 
public class InterestEvent extends ScheduledEvent{

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_id", nullable = false)
    private FinancialAccount account;

    private YearMonth yearMonth;

    //for transactions
    public String getDescription() {
        return "Interest for " + account.getName() + " for " + yearMonth.getMonth() + " " + yearMonth.getYear(); 
    }

    public FinancialAccount getAccount() {
        return account;
    }
}
