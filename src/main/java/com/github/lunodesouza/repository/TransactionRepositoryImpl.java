package com.github.lunodesouza.repository;

import com.github.lunodesouza.dto.DailySummaryReport;
import com.github.lunodesouza.exception.TransactionBadRequestException;
import com.github.lunodesouza.exception.TransactionNotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Slf4j
public class TransactionRepositoryImpl implements TransactionRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<DailySummaryReport> getDailySummaryReport() {
        try {
            String queryString = "SELECT NEW com.github.lunodesouza.dto.DailySummaryReport(t.date, " +
                    "SUM(CASE WHEN t.type = 'CREDIT' THEN t.amount ELSE 0 END), " +
                    "SUM(CASE WHEN t.type = 'DEBIT' THEN t.amount ELSE 0 END), " +
                    "SUM(CASE WHEN t.type = 'CREDIT' THEN t.amount ELSE -t.amount END)) " +
                    "FROM Transaction t " +
                    "GROUP BY t.date";
            TypedQuery<DailySummaryReport> query = entityManager.createQuery(queryString, DailySummaryReport.class);

            return Optional.ofNullable(query.getResultList())
                    .filter(list -> !list.isEmpty())
                    .orElseThrow(() -> new TransactionNotFoundException("Transaction Not Found."));
        } catch(Exception e) {
            log.error("Error in Transaction: {}", e.getMessage());
            throw new TransactionBadRequestException("Error in Transaction: "+ e.getMessage());
        }
    }
}
