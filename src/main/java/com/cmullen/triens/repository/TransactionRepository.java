package com.cmullen.triens.repository;

import com.cmullen.triens.model.Transaction;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository
    extends JpaRepository<Transaction, UUID> {}