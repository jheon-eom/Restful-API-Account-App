package com.ejh.accountapp.bank.service;

import com.ejh.accountapp.bank.domain.account.Account;
import com.ejh.accountapp.bank.domain.transaction.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;

    @Transactional
    public void saveDepositHistory(Account fromAccount, Account toAccount) {
    }
}
