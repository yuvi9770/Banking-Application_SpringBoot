package com.Banking.controller;

import com.Banking.model.Transaction;
import com.Banking.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/history/{accountId}")
    public ResponseEntity<List<Transaction>> getTransactions(@PathVariable Long accountId) {
        List<Transaction> transactions = transactionService.getTransactionsByAccount(accountId);
        return ResponseEntity.ok(transactions);
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestBody Transaction transaction) {
        transactionService.transfer(
                transaction.getFromAccountId(),
                transaction.getToAccountId(),
                transaction.getAmount()
        );
        return ResponseEntity.ok(
                "Transfer successful from Account " + transaction.getFromAccountId() +
                        " to Account " + transaction.getToAccountId()
        );
    }
}