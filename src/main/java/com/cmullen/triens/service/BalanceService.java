package com.cmullen.triens.service;

import com.cmullen.triens.model.DailyBalance;
import com.cmullen.triens.model.HistoricalPrice;
import com.cmullen.triens.model.LedgerEntry;
import com.cmullen.triens.model.SecurityEntry;
import com.cmullen.triens.model.TransactionEntry;
import com.cmullen.triens.repository.HistoricalPriceRepository;
import com.cmullen.triens.repository.TransactionEntryRepository;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class BalanceService {

  private final TransactionEntryRepository transactionEntryRepository;
  private final HistoricalPriceRepository historicalPriceRepository;

  public BalanceService(
      TransactionEntryRepository transactionEntryRepository,
      HistoricalPriceRepository historicalPriceRepository) {
    this.transactionEntryRepository = transactionEntryRepository;
    this.historicalPriceRepository = historicalPriceRepository;
  }

  public DailyBalance getBalance(UUID account, LocalDate date) {
    BigDecimal cashBalance = BigDecimal.ZERO;
    BigDecimal holdingsBalance = BigDecimal.ZERO;
    BigDecimal unsettledCashDelta = BigDecimal.ZERO;
    BigDecimal unsettledHoldingsDelta = BigDecimal.ZERO;

    Map<UUID, HistoricalPrice> historicalPrices = new HashMap<>();

    List<TransactionEntry> entries =
        transactionEntryRepository.findByAccount_IdAndTransaction_EffectiveDateLessThanEqual(
            account, date);

    for (TransactionEntry entry : entries) {

      Instant settledAt = entry.getSettlementInstant();
      boolean settled =
          settledAt != null
              && !LocalDate.ofInstant(settledAt, ZoneId.of("America/Chicago")).isAfter(date);

      if (entry instanceof LedgerEntry) {
        LedgerEntry ledgerEntry = (LedgerEntry) entry;

        if (settled) {
          cashBalance = cashBalance.add(ledgerEntry.getAmount());
        } else {
          unsettledCashDelta = unsettledCashDelta.add(ledgerEntry.getAmount());
        }
      } else if (entry instanceof SecurityEntry) {
        SecurityEntry securityEntry = (SecurityEntry) entry;
        BigDecimal price =
            historicalPrices
                .computeIfAbsent(
                    securityEntry.getSecurity().getUUID(),
                    id ->
                        historicalPriceRepository
                            .findTopBySecurity_IdAndDateLessThanEqualOrderByDateDesc(id, date)
                            .orElseThrow())
                .getClosingPrice();

        if (settled) {
          holdingsBalance = holdingsBalance.add(securityEntry.getQuantity().multiply(price));
        } else {
          unsettledHoldingsDelta =
              unsettledHoldingsDelta.add(securityEntry.getQuantity().multiply(price));
        }
      }
    }

    return new DailyBalance(
        date, cashBalance, holdingsBalance, unsettledCashDelta, unsettledHoldingsDelta);
  }

  // inclusive of both ends
  public List<DailyBalance> getBalances(UUID account, LocalDate start, LocalDate end) {
    if (start.isAfter(end)) {
      throw new IllegalArgumentException("Start date must not be after end date");
    }

    List<DailyBalance> balances = new ArrayList<>();
    List<TransactionEntry> transactionEntries =
        transactionEntryRepository
            .findByAccount_IdAndTransaction_EffectiveDateLessThanEqualOrderByTransaction_EffectiveDateAsc(
                account, end);

    List<TransactionEntry> unsettledEntries = new ArrayList<>();

    BigDecimal cashBalance = BigDecimal.ZERO;
    BigDecimal unsettledCashDelta = BigDecimal.ZERO;
    List<UUID> relevantSecurities = new ArrayList<>();
    Map<UUID, BigDecimal> holdings = new HashMap<>();
    Map<UUID, BigDecimal> unsettledHoldings = new HashMap<>();

    // Begin at the earliest transaction date, or start if no earlier transactions exist.
    LocalDate current = start;
    if (!transactionEntries.isEmpty()) {
      LocalDate firstDate = transactionEntries.getFirst().getTransaction().getEffectiveDate();
      if (firstDate.isBefore(start)) {
        current = firstDate;
      }
    }

    int entryIndex = 0;

    while (!current.isAfter(end)) {

      // Process transactions effective on the current date.
      if (entryIndex < transactionEntries.size()
          && !transactionEntries
              .get(entryIndex)
              .getTransaction()
              .getEffectiveDate()
              .isAfter(current)) {

        TransactionEntry entry = transactionEntries.get(entryIndex);

        Instant settledAt = entry.getSettlementInstant();
        boolean settled =
            settledAt != null
                && !LocalDate.ofInstant(settledAt, ZoneId.of("America/Chicago")).isAfter(current);

        if (entry instanceof LedgerEntry) {
          LedgerEntry ledgerEntry = (LedgerEntry) entry;

          if (settled) {
            cashBalance = cashBalance.add(ledgerEntry.getAmount());
          } else {
            unsettledCashDelta = unsettledCashDelta.add(ledgerEntry.getAmount());
            unsettledEntries.add(entry);
          }

        } else if (entry instanceof SecurityEntry) {
          SecurityEntry securityEntry = (SecurityEntry) entry;
          UUID securityId = securityEntry.getSecurity().getUUID();

          if (!relevantSecurities.contains(securityId)) {
            relevantSecurities.add(securityId);
          }

          if (settled) {
            holdings.merge(securityId, securityEntry.getQuantity(), BigDecimal::add);
          } else {
            unsettledHoldings.merge(securityId, securityEntry.getQuantity(), BigDecimal::add);
            unsettledEntries.add(entry);
          }
        }

        entryIndex++;
        continue;
      }

      // All transactions effective on this date have been processed.
      // Process outstanding settlements before recording the day's balance.
      Iterator<TransactionEntry> iterator = unsettledEntries.iterator();

      while (iterator.hasNext()) {
        TransactionEntry unsettledEntry = iterator.next();

        Instant settledAt = unsettledEntry.getSettlementInstant();
        boolean settled =
            settledAt != null
                && !LocalDate.ofInstant(settledAt, ZoneId.of("America/Chicago")).isAfter(current);

        if (settled) {
          if (unsettledEntry instanceof LedgerEntry) {
            LedgerEntry ledgerEntry = (LedgerEntry) unsettledEntry;

            cashBalance = cashBalance.add(ledgerEntry.getAmount());
            unsettledCashDelta = unsettledCashDelta.subtract(ledgerEntry.getAmount());

          } else if (unsettledEntry instanceof SecurityEntry) {
            SecurityEntry securityEntry = (SecurityEntry) unsettledEntry;
            UUID securityId = securityEntry.getSecurity().getUUID();

            holdings.merge(securityId, securityEntry.getQuantity(), BigDecimal::add);

            unsettledHoldings.merge(securityId, securityEntry.getQuantity(), BigDecimal::subtract);
          }

          iterator.remove();
        }
      }

      // Record the balance only if within the requested range.
      if (!current.isBefore(start)) {
        Map<UUID, HistoricalPrice> historicalPrices = new HashMap<>();

        for (UUID securityId : relevantSecurities) {
          historicalPrices.put(
              securityId,
              historicalPriceRepository
                  .findTopBySecurity_IdAndDateLessThanEqualOrderByDateDesc(securityId, current)
                  .orElseThrow());
        }

        BigDecimal holdingsValue = BigDecimal.ZERO;
        for (Map.Entry<UUID, BigDecimal> holdingsEntry : holdings.entrySet()) {
          UUID securityId = holdingsEntry.getKey();
          BigDecimal quantity = holdingsEntry.getValue();

          holdingsValue =
              holdingsValue.add(
                  quantity.multiply(historicalPrices.get(securityId).getClosingPrice()));
        }

        BigDecimal unsettledHoldingsDelta = BigDecimal.ZERO;
        for (Map.Entry<UUID, BigDecimal> holdingsEntry : unsettledHoldings.entrySet()) {
          UUID securityId = holdingsEntry.getKey();
          BigDecimal quantity = holdingsEntry.getValue();

          unsettledHoldingsDelta =
              unsettledHoldingsDelta.add(
                  quantity.multiply(historicalPrices.get(securityId).getClosingPrice()));
        }

        DailyBalance db =
            new DailyBalance(
                current, cashBalance, holdingsValue, unsettledCashDelta, unsettledHoldingsDelta);

        balances.add(db);
      }

      // Advance to the next calendar day, including days without transactions.
      if (current.equals(end)) {
        break;
      }
      current = current.plusDays(1);
    }

    return balances;
  }
}