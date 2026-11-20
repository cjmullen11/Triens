package com.cmullen.triens.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cmullen.triens.model.DailyBalance;
import com.cmullen.triens.model.LedgerEntry;

public interface LedgerEntryRepository
        extends JpaRepository<LedgerEntry, UUID> {
                

    //get a single DailyBalance
    @Query("""
        SELECT new com.cmullen.triens.model.DailyBalance(
            :date,
            COALESCE(SUM(e.amount), 0)
        )
        FROM LedgerEntry e
        WHERE e.account.id = :accountId
        AND e.transaction.effectiveDate <= :date
        AND e.settledAt IS NOT NULL
        """)
    DailyBalance getBalanceAtDate(
        UUID accountId,
        LocalDate date
    );


}
