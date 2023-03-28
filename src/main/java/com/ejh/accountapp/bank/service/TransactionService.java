package com.ejh.accountapp.bank.service;

import com.ejh.accountapp.bank.domain.account.Account;
import com.ejh.accountapp.bank.domain.account.AccountRepository;
import com.ejh.accountapp.bank.domain.transaction.Transaction;
import com.ejh.accountapp.bank.domain.transaction.TransactionRepository;
import com.ejh.accountapp.bank.dto.account.DepositRequestDto;
import com.ejh.accountapp.bank.dto.transaction.TransactionResponseDto;
import com.ejh.accountapp.bank.handler.exception.CustomApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    public List<TransactionResponseDto> findTransactionList(Long accountNumber, String type, Long userId, Integer page) {
        // 계좌 확인
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new CustomApiException("계좌를 찾을 수 없습니다."));
        // 계좌 소유자 확인
        account.checkOwner(userId);
        // 거래 내역 조회
        List<Transaction> transactionList = transactionRepository.findTransactionList(account.getId(), type, page);
        // DTO 변환
        List<TransactionResponseDto> transactionResponseDtoList = transactionList.stream().map(
                transaction -> new TransactionResponseDto(transaction)).collect(Collectors.toList());
        return transactionResponseDtoList;
    }

    @Transactional
    public void saveDepositHistory(DepositRequestDto depositRequestDto, Account fromAccount, Account toAccount) {
        Transaction transaction = Transaction.builder()
                .depositAccount(fromAccount)
                .receiveAccount(toAccount)
                .amount(depositRequestDto.getAmount())
                .transactionType(depositRequestDto.getTransactionType())
                .sender(fromAccount.getUser().getUsername())
                .receiver(toAccount.getUser().getUsername())
                .build();
        transactionRepository.save(transaction);
    }
}
