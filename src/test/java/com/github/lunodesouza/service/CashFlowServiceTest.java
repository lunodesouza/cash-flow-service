package com.github.lunodesouza.service;

import com.github.lunodesouza.domain.Transaction;
import com.github.lunodesouza.domain.TransactionType;
import com.github.lunodesouza.dto.DailySummaryReport;
import com.github.lunodesouza.exception.TransactionNotFoundException;
import com.github.lunodesouza.repository.TransactionRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class CashFlowServiceTest {
    @Autowired
    CashFlowService cashFlowService;
    @Autowired
    TransactionRepository transactionRepository;

    @BeforeEach
    void setUp() {
        Transaction transaction1 = new Transaction();
        transaction1.setAmount(new BigDecimal("100.00"));
        transaction1.setType(TransactionType.CREDIT);
        transaction1.setDate(LocalDate.of(2020, 1, 1));
        transactionRepository.save(transaction1);

        Transaction transaction2 = new Transaction();
        transaction2.setAmount(new BigDecimal("50.00"));
        transaction2.setType(TransactionType.DEBIT);
        transaction2.setDate(LocalDate.of(2020, 1, 1));
        transactionRepository.save(transaction2);

        Transaction transaction3 = new Transaction();
        transaction3.setAmount(new BigDecimal("250.00"));
        transaction3.setType(TransactionType.CREDIT);
        transaction3.setDate(LocalDate.of(2020, 1, 2));
        transactionRepository.save(transaction3);

        Transaction transaction4 = new Transaction();
        transaction4.setAmount(new BigDecimal("100.00"));
        transaction4.setType(TransactionType.DEBIT);
        transaction4.setDate(LocalDate.of(2020, 1, 2));
        transactionRepository.save(transaction4);
    }

    @AfterEach
    void tearDown() {
        transactionRepository.deleteAll();
    }

    @Test
    @DisplayName("Should throw TransactionNotFoundException when the transaction is null")
    void saveTransactionWhenNullThenThrowTransactionNotFoundException() {
        Transaction transaction = null;

        try {
            cashFlowService.saveTransaction(transaction);
        } catch (TransactionNotFoundException e) {
            assertEquals("Error in Transaction: Transaction can't be null.", e.getMessage());
        }
    }

    @Test
    @DisplayName("Should save the transaction when it is not null")
    void saveTransactionWhenNotNull() {
        Transaction transaction = new Transaction();
        transaction.setAmount(new BigDecimal("500.00"));
        transaction.setType(TransactionType.CREDIT);
        transaction.setDate(LocalDate.of(2021, 1, 1));

        Transaction savedTransaction = cashFlowService.saveTransaction(transaction);

        assertNotNull(savedTransaction.getId());
        assertEquals(transaction.getAmount(), savedTransaction.getAmount());
        assertEquals(transaction.getType(), savedTransaction.getType());
        assertEquals(transaction.getDate(), savedTransaction.getDate());
    }

    @Test
    @DisplayName("Should return a list of daily summary reports with correct balance")
    void generateDailySummaryReport() {
        List<DailySummaryReport> dailySummaryReports = cashFlowService.generateDailySummaryReport();

        assertNotNull(dailySummaryReports);
        assertEquals(2, dailySummaryReports.size());

        DailySummaryReport dailySummaryReport1 = dailySummaryReports.get(0);
        assertEquals(LocalDate.of(2020, 1, 1), dailySummaryReport1.getDate());
        assertEquals(new BigDecimal("100.00"), dailySummaryReport1.getTotalCredits());
        assertEquals(new BigDecimal("50.00"), dailySummaryReport1.getTotalDebits());
        assertEquals(new BigDecimal("50.00"), dailySummaryReport1.getBalance());

        DailySummaryReport dailySummaryReport2 = dailySummaryReports.get(1);
        assertEquals(LocalDate.of(2020, 1, 2), dailySummaryReport2.getDate());
        assertEquals(new BigDecimal("250.00"), dailySummaryReport2.getTotalCredits());
        assertEquals(new BigDecimal("100.00"), dailySummaryReport2.getTotalDebits());
        assertEquals(new BigDecimal("150.00"), dailySummaryReport2.getBalance());
    }
}