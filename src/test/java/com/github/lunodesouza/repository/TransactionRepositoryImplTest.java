package com.github.lunodesouza.repository;

import com.github.lunodesouza.dto.DailySummaryReport;
import com.github.lunodesouza.exception.TransactionBadRequestException;
import com.github.lunodesouza.exception.TransactionNotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionRepositoryImplTest {

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private TransactionRepositoryImpl transactionRepository;

    @Test
    @DisplayName("Should throw a TransactionNotFoundException when no transactions are found")
    void getDailySummaryReportWhenNoTransactionsFoundThenThrowException() {
        TypedQuery<DailySummaryReport> query = mock(TypedQuery.class);
        when(entityManager.createQuery(anyString(), eq(DailySummaryReport.class)))
                .thenReturn(query);
        when(query.getResultList()).thenReturn(Collections.emptyList());

        // when, then
        TransactionNotFoundException exception =
                assertThrows(
                        TransactionNotFoundException.class,
                        () -> transactionRepository.getDailySummaryReport());
        assertEquals("Error in Transaction: Transaction Not Found.", exception.getMessage());
    }

    @Test
    @DisplayName(
            "Should throw a TransactionBadRequestException when there is an error in the query")
    void getDailySummaryReportWhenQueryErrorThenThrowException() {
        String queryString =
                "SELECT NEW com.github.lunodesouza.dto.DailySummaryReport(t.date, "
                        + "SUM(CASE WHEN t.type = 'CREDIT' THEN t.amount ELSE 0 END), "
                        + "SUM(CASE WHEN t.type = 'DEBIT' THEN t.amount ELSE 0 END), "
                        + "SUM(CASE WHEN t.type = 'CREDIT' THEN t.amount ELSE -t.amount END)) "
                        + "FROM Transaction t "
                        + "GROUP BY t.date";
        TypedQuery<DailySummaryReport> query = mock(TypedQuery.class);
        when(entityManager.createQuery(queryString, DailySummaryReport.class)).thenReturn(query);
        when(query.getResultList()).thenThrow(new RuntimeException("Error executing query"));

        // Act and Assert
        assertThrows(
                TransactionBadRequestException.class,
                () -> {
                    transactionRepository.getDailySummaryReport();
                });
    }

    @Test
    @DisplayName("Should return a list of daily summary reports when transactions are found")
    void getDailySummaryReportWhenTransactionsFound() {
        String queryString =
                "SELECT NEW com.github.lunodesouza.dto.DailySummaryReport(t.date, "
                        + "SUM(CASE WHEN t.type = 'CREDIT' THEN t.amount ELSE 0 END), "
                        + "SUM(CASE WHEN t.type = 'DEBIT' THEN t.amount ELSE 0 END), "
                        + "SUM(CASE WHEN t.type = 'CREDIT' THEN t.amount ELSE -t.amount END)) "
                        + "FROM Transaction t "
                        + "GROUP BY t.date";
        TypedQuery<DailySummaryReport> query = mock(TypedQuery.class);
        List<DailySummaryReport> dailySummaryReports =
                Collections.singletonList(
                        new DailySummaryReport(
                                LocalDate.now(),
                                BigDecimal.valueOf(100),
                                BigDecimal.valueOf(50),
                                BigDecimal.valueOf(50)));
        when(entityManager.createQuery(queryString, DailySummaryReport.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(dailySummaryReports);

        List<DailySummaryReport> result = transactionRepository.getDailySummaryReport();

        assertNotNull(result);
        assertEquals(dailySummaryReports, result);
        verify(entityManager, times(1)).createQuery(queryString, DailySummaryReport.class);
        verify(query, times(1)).getResultList();
    }

    @Test
    @DisplayName(
            "Should throw a TransactionBadRequestException when an error occurs during the query execution")
    void getDailySummaryReportWhenErrorOccursThenThrowException() {
        String errorMessage = "Error in Transaction: Something went wrong";
        when(entityManager.createQuery(anyString(), eq(DailySummaryReport.class)))
                .thenThrow(new RuntimeException(errorMessage));

        TransactionBadRequestException exception =
                assertThrows(
                        TransactionBadRequestException.class,
                        () -> {
                            transactionRepository.getDailySummaryReport();
                        });

        assertEquals("Error in Transaction: " + errorMessage, exception.getMessage());
    }
}