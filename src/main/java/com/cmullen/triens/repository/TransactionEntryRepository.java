package com.cmullen.triens.repository;

import com.cmullen.triens.model.TransactionEntry;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionEntryRepository
    extends JpaRepository<TransactionEntry, UUID> {}