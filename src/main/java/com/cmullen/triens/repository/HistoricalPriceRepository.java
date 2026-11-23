package com.cmullen.triens.repository;

import com.cmullen.triens.model.HistoricalPrice;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoricalPriceRepository extends JpaRepository<HistoricalPrice, UUID> {
  Optional<HistoricalPrice> findTopBySecurity_IdAndDateLessThanEqualOrderByDateDesc(
      UUID securityId, LocalDate date);
}
