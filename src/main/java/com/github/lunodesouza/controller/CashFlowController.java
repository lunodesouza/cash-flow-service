package com.github.lunodesouza.controller;

import com.github.lunodesouza.domain.Transaction;
import com.github.lunodesouza.dto.DailySummaryReport;
import com.github.lunodesouza.service.CashFlowService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CashFlowController {
    @Autowired
    private CashFlowService cashFlowService;

    @Operation(summary = "Get daily summary report")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found Transaction",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DailySummaryReport.class)) })
            })
    @GetMapping("/daily-summary")
    public ResponseEntity<?> getDailySummaryReport() {
        return ResponseEntity.ok(cashFlowService.generateDailySummaryReport());
    }


    @Operation(summary = "Send a new Transaction")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transaction saved",
                    content = {
                    @Content(mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = Transaction.class)))
            })
    })
    @PostMapping("/transactions")
    public ResponseEntity<Transaction> saveTransaction(@RequestBody Transaction transaction) {
        return ResponseEntity.ok(cashFlowService.saveTransaction(transaction));
    }
}
