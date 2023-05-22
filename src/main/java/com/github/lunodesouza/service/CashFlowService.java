package com.github.lunodesouza.service;

import com.github.lunodesouza.dto.DailySummaryReport;
import com.github.lunodesouza.domain.Transaction;
import com.github.lunodesouza.exception.TransactionBadRequestException;
import com.github.lunodesouza.exception.TransactionNotFoundException;
import com.github.lunodesouza.repository.TransactionRepository;
import com.github.lunodesouza.repository.TransactionRepositoryCustom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.nonNull;

@Service
@Slf4j
public class CashFlowService {
    @Autowired
    TransactionRepositoryCustom transactionRepositoryCustom;
    @Autowired
    TransactionRepository transactionRepository;

    public List<DailySummaryReport> generateDailySummaryReport() {
        return transactionRepositoryCustom.getDailySummaryReport();
    }

    public Transaction saveTransacition(Transaction transaction){
        try{
            if(nonNull(transaction)){
                transactionRepository.save(transaction);
            } else {
                throw new TransactionNotFoundException("Transaction can't be null.");
            }
        } catch(Exception e){
            log.error("saveTransacition fail: {}", e.getMessage());
            throw new TransactionBadRequestException("Error in Transaction: "+ e.getMessage());
        }
        return transaction;
    }
}
