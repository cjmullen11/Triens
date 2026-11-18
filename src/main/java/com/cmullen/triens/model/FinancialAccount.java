package com.cmullen.triens.model;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity 
public abstract class FinancialAccount extends LedgerAccount{
    @OneToOne(
        fetch = FetchType.LAZY,
        optional = false,
        cascade = CascadeType.PERSIST
    )
    @JoinColumn(
        name = "interest_policy_id",
        nullable = false,
        unique = true
    )
    private InterestPolicy interestPolicy;


    public abstract BigDecimal calculateInterest(List<DailyBalance> balances);
}