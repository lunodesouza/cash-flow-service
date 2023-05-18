package com.github.lunodesouza.controller;

import com.github.lunodesouza.domain.Transaction;
import com.github.lunodesouza.service.CashFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CashFlowController {
    @Autowired
    private CashFlowService cashFlowService;

    @GetMapping("/daily-summary")
    public ResponseEntity<?> getDailySummaryReport() {
        return ResponseEntity.ok(cashFlowService.generateDailySummaryReport());
    }

    @PostMapping("/transactions")
    public ResponseEntity<Transaction> saveTransaction(@RequestBody Transaction transaction) {
        return ResponseEntity.ok(cashFlowService.saveTransacition(transaction));
    }
}
