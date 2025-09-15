package com.Banking.service;

import com.Banking.model.Account;
import com.Banking.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    // GetAll
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    // Create
    public void createAccount(Account account) {
        accountRepository.save(account);
    }

    // Get by ID
    public Account getAccountById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found with ID: " + id));
    }

    // Update
    public void updateAccount(Long id, Account account) {
        Account existingAccount = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found with ID: " + id));

        existingAccount.setName(account.getName());
        existingAccount.setAge(account.getAge());
        existingAccount.setGender(account.getGender());
        existingAccount.setCity(account.getCity());
        existingAccount.setBalance(account.getBalance());

        accountRepository.save(existingAccount);
    }

    // Delete
    public void deleteAccount(Long id) {
        if (!accountRepository.existsById(id)) {
            throw new RuntimeException("Account not found with ID: " + id);
        }
        accountRepository.deleteById(id);
    }
}