package com.cmullen.triens.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.*;

@Entity
@Table(
    name = "historical_price",
    uniqueConstraints = @UniqueConstraint(
        columnNames = {"security_id", "date"}
    )
)
public class HistoricalPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "security_id", nullable = false)
    private Security security;

    @Column(nullable = false)
    private BigDecimal closingPrice;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private Instant retrievedAt;

    @PrePersist
    protected void onCreate() {
        retrievedAt = Instant.now();
    }
}
