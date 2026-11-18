package com.cmullen.triens.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public class HistoricalPrice {
    private UUID id;
    private Security security;
    private BigDecimal closingPrice;
    private LocalDate date;
    private Instant retrievedAt;
}
