package com.cmullen.triens.repository;

import com.cmullen.triens.model.TransactionEntry;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionEntryRepository extends JpaRepository<TransactionEntry, UUID> {

  List<TransactionEntry> findByAccount_IdAndTransaction_EffectiveDateLessThanEqual(
      UUID accountId, LocalDate date);

  List<TransactionEntry>
      findByAccount_IdAndTransaction_EffectiveDateLessThanEqualOrderByTransaction_EffectiveDateAsc(
          UUID accountId, LocalDate end);
}