package com.cmullen.triens.repository;

import com.cmullen.triens.model.ScheduledEvent;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduledEventRepository extends JpaRepository<ScheduledEvent, UUID> {

  List<ScheduledEvent> findByActiveTrueAndNextRunLessThan(LocalDate date);
}
