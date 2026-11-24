package com.cmullen.triens.repository;

import com.cmullen.triens.model.ScheduledEvent;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduledEventRepository extends JpaRepository<ScheduledEvent, UUID> {}
