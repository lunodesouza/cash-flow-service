package com.github.lunodesouza.repository;

import com.github.lunodesouza.dto.DailySummaryReport;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class TransactionRepositoryImpl implements TransactionRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<DailySummaryReport> getDailySummaryReport() {
        String queryString = "SELECT NEW com.github.lunodesouza.dto.DailySummaryReport(t.date, " +
                "SUM(CASE WHEN t.type = 'CREDIT' THEN t.amount ELSE 0 END), " +
                "SUM(CASE WHEN t.type = 'DEBIT' THEN t.amount ELSE 0 END), " +
                "SUM(CASE WHEN t.type = 'CREDIT' THEN t.amount ELSE -t.amount END)) " +
                "FROM Transaction t " +
                "GROUP BY t.date";
        TypedQuery<DailySummaryReport> query = entityManager.createQuery(queryString, DailySummaryReport.class);
        return query.getResultList();
    }
}
