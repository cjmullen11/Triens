package com.cmullen.triens.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cmullen.triens.model.Transaction;

public interface TransactionRepository
        extends JpaRepository<Transaction, UUID> {
}