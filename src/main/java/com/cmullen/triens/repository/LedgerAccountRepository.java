package com.cmullen.triens.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cmullen.triens.model.LedgerAccount;

public interface LedgerAccountRepository
        extends JpaRepository<LedgerAccount, UUID> {
}