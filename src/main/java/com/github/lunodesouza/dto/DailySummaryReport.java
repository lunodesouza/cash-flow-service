package com.github.lunodesouza.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class DailySummaryReport {
    private LocalDate date;
    private BigDecimal totalCredits;
    private BigDecimal totalDebits;
    private BigDecimal balance;

}
