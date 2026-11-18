package com.cmullen.triens.model;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;

import com.cmullen.triens.repository.LedgerAccountRepository;
import com.cmullen.triens.repository.TransactionRepository;
import com.cmullen.triens.repository.SecurityRepository;

import jakarta.persistence.EntityManager;

@DataJpaTest(properties = "spring.jpa.hibernate.ddl-auto=validate")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("dev")
class PersistenceIntegrationTest {

    @Autowired
    private LedgerAccountRepository accounts;

    @Autowired
    private TransactionRepository transactions;

    @Autowired
    private SecurityRepository securities;

    @Autowired
    private EntityManager em;

    @Test
    void testBankAccount() {
        BankAccount account = createBankAccount("Checking");

        UUID id = (UUID) ReflectionTestUtils.getField(account, "id");

        em.clear();

        LedgerAccount loaded = accounts.findById(id).orElseThrow();

        assertInstanceOf(BankAccount.class, loaded);
        assertEquals("Checking", ReflectionTestUtils.getField(loaded, "name"));
        assertNotNull(ReflectionTestUtils.getField(loaded, "interestPolicy"));
    }

    @Test
    void testSecurity() {
        Security security = new Security();

        ReflectionTestUtils.setField(security, "ticker", "AAPL");
        ReflectionTestUtils.setField(security, "name", "Apple Inc.");
        ReflectionTestUtils.setField(security, "active", true);

        securities.saveAndFlush(security);

        UUID id = (UUID) ReflectionTestUtils.getField(security, "id");

        em.clear();

        Security loaded = securities.findById(id).orElseThrow();

        assertEquals("AAPL", ReflectionTestUtils.getField(loaded, "ticker"));
        assertEquals("Apple Inc.", ReflectionTestUtils.getField(loaded, "name"));
    }

    @Test
    void testTransactionEntries() {
        BankAccount checking = createBankAccount("Checking");
        BankAccount savings = createBankAccount("Savings");

        LedgerEntry debit = new LedgerEntry();
        ReflectionTestUtils.setField(debit, "account", checking);
        ReflectionTestUtils.setField(debit, "amount", new BigDecimal("-100.00"));

        LedgerEntry credit = new LedgerEntry();
        ReflectionTestUtils.setField(credit, "account", savings);
        ReflectionTestUtils.setField(credit, "amount", new BigDecimal("100.00"));

        Transaction transaction = new Transaction();

        ReflectionTestUtils.setField(transaction, "type", TransactionType.ACCOUNT_TRANSFER);
        ReflectionTestUtils.setField(transaction, "status", TransactionStatus.INITIATED);
        ReflectionTestUtils.setField(transaction, "effectiveDate", LocalDate.now());
        ReflectionTestUtils.setField(
            transaction,
            "entries",
            new ArrayList<>(List.of(debit, credit))
        );

        transactions.saveAndFlush(transaction);

        UUID id = (UUID) ReflectionTestUtils.getField(transaction, "id");

        em.clear();

        Transaction loaded = transactions.findById(id).orElseThrow();

        List<?> entries = (List<?>) ReflectionTestUtils.getField(loaded, "entries");

        assertEquals(2, entries.size());
    }

    private BankAccount createBankAccount(String name) {
        NoInterestPolicy policy = new NoInterestPolicy();
        ReflectionTestUtils.setField(policy, "annualRate", BigDecimal.ZERO);

        BankAccount account = new BankAccount();

        ReflectionTestUtils.setField(account, "name", name);
        ReflectionTestUtils.setField(account, "currency", Currency.getInstance("USD"));
        ReflectionTestUtils.setField(account, "interestPolicy", policy);

        return (BankAccount) accounts.saveAndFlush(account);
    }
}
