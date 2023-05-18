package com.github.lunodesouza.repository;

import com.github.lunodesouza.dto.DailySummaryReport;

import java.util.List;

public interface TransactionRepositoryCustom {
    List<DailySummaryReport> getDailySummaryReport();
}
