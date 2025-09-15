package com.Banking.repository;

import com.Banking.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    List<Transaction> findByFromAccountIdOrToAccountIdOrderByDateDesc(Long accountId, Long accountId1);
}