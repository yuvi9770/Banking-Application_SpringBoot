package com.Banking.service;

import com.Banking.model.Account;
import com.Banking.model.Transaction;
import com.Banking.repository.AccountRepository;
import com.Banking.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    // get transaction history
    public List<Transaction> getTransactionsByAccount(Long accountId) {
        return transactionRepository.findByFromAccountIdOrToAccountIdOrderByDateDesc(accountId, accountId);
    }

    // transfer amount
    public void transfer(Long fromAccountId, Long toAccountId, double amount) {
        Account fromAccount = accountRepository.findById(fromAccountId)
                .orElseThrow(() -> new RuntimeException("Source account not found with ID: " + fromAccountId));

        Account toAccount = accountRepository.findById(toAccountId)
                .orElseThrow(() -> new RuntimeException("Destination account not found with ID: " + toAccountId));

        if (fromAccount.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient funds in source account.");
        }

        // update balances
        fromAccount.setBalance(fromAccount.getBalance() - amount);
        toAccount.setBalance(toAccount.getBalance() + amount);

        // create transaction record
        Transaction transaction = new Transaction();
        transaction.setFromAccountId(fromAccountId);
        transaction.setToAccountId(toAccountId);
        transaction.setAmount(amount);

        // save transaction & accounts
        transactionRepository.save(transaction);
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
    }
}
