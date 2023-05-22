package com.github.lunodesouza.controller;

import com.github.lunodesouza.domain.Transaction;
import com.github.lunodesouza.domain.TransactionType;
import com.github.lunodesouza.dto.DailySummaryReport;
import com.github.lunodesouza.service.CashFlowService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;

import static com.github.lunodesouza.util.TestUtils.asJsonString;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = CashFlowController.class)
class CashFlowControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CashFlowService cashFlowService;

    @Test
    @DisplayName("Should return daily summary report successfully")
    void
    getDailySummaryReportSuccess() throws Exception { // Create a DailySummaryReport object to be returned by
        // the mocked CashFlowService
        DailySummaryReport dailySummaryReport =
                new DailySummaryReport(
                        LocalDate.now(),
                        new BigDecimal("100.00"),
                        new BigDecimal("50.00"),
                        new BigDecimal("50.00"));
        when(cashFlowService.generateDailySummaryReport()).thenReturn(Collections.singletonList(dailySummaryReport));

        // Perform the GET request to /api/daily-summary
        ResultActions resultActions =
                mockMvc.perform(get("/api/daily-summary").contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());

        // Verify that the response contains the expected DailySummaryReport object
        resultActions
                .andExpect(jsonPath("$[0].date", is(dailySummaryReport.getDate().toString())))
                .andExpect(jsonPath("$[0].totalCredits", is(dailySummaryReport.getTotalCredits().doubleValue())))
                .andExpect(jsonPath("$[0].totalDebits", is(dailySummaryReport.getTotalDebits().doubleValue())))
                .andExpect(jsonPath("$[0].balance", is(dailySummaryReport.getBalance().doubleValue())));

        // Verify that the CashFlowService method was called once
        verify(cashFlowService, times(1)).generateDailySummaryReport();
    }

    @Test
    @DisplayName("Should save the transaction and return the saved transaction")
    void saveTransactionReturnsSavedTransaction() throws Exception { // Create a new transaction
        Transaction transaction =
                new Transaction(
                        1L,
                        new BigDecimal("100.00"),
                        TransactionType.CREDIT,
                        LocalDate.now(),
                        null,
                        null);

        // Mock the behavior of the cashFlowService.saveTransaction() method
        when(cashFlowService.saveTransaction(any(Transaction.class))).thenReturn(transaction);

        // Send a POST request to the /transactions endpoint with the transaction object as the
        // request body
        ResultActions credit = mockMvc.perform(
                        post("/api/transactions")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(transaction)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.amount", is(100.00)))
                .andExpect(jsonPath("$.type", is("CREDIT")))
                .andExpect(jsonPath("$.date", is(LocalDate.now().toString())));

        // Verify that the cashFlowService.saveTransaction() method was called exactly once with the
        // transaction object
        verify(cashFlowService, times(1)).saveTransaction(eq(transaction));
    }
}