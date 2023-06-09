package com.ejh.accountapp.bank.domain.transaction;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long>, CustomTransactionDao {
}
