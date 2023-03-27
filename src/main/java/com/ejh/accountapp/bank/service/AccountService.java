package com.ejh.accountapp.bank.service;

import com.ejh.accountapp.bank.domain.account.Account;
import com.ejh.accountapp.bank.domain.account.AccountRepository;
import com.ejh.accountapp.bank.domain.user.User;
import com.ejh.accountapp.bank.domain.user.UserRepository;
import com.ejh.accountapp.bank.dto.account.*;
import com.ejh.accountapp.bank.handler.exception.CustomApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final TransactionService transactionService;

    @Transactional
    public CreateAccountResponseDto createAccount(CreateAccountRequestDto createAccountRequestDto, Long userId) {
        // 사용자 확인
        User user = userRepository.findById(userId).orElseThrow(
                () -> new CustomApiException("존재하지 않는 사용자입니다."));
        // 계좌 중복 확인
        Optional<Account> account = accountRepository.findByAccountNumber(createAccountRequestDto.getAccountNumber());
        if (account.isPresent()) {
            throw new CustomApiException("이미 등록된 계좌번호입니다.");
        }
        // 계좌 등록
        Account saveAccount = accountRepository.save(createAccountRequestDto.toEntity(user));
        return new CreateAccountResponseDto(saveAccount);
    }

    public List<AccountResponseDto> getAccountList(Long userId) {
        List<Account> accountList = accountRepository.findByUserId(userId);
        List<AccountResponseDto> accountResponseDtoList = accountList.stream().map(
                account -> new AccountResponseDto(account)).collect(Collectors.toList());
        return accountResponseDtoList;
    }

    public AccountDetailResponseDto getAccountDetail(AccountDetailRequestDto accountDetailRequestDto, Long userId) {
        // 계좌 확인
        Account account = accountRepository.findByAccountNumber(accountDetailRequestDto.getAccountNumber())
                .orElseThrow(() -> new CustomApiException("존재하지 않는 계좌입니다."));
        // 계좌 소유자 확인
        account.checkOwner(userId);
        // 계좌 상세 조회
        return new AccountDetailResponseDto(account);
    }

    @Transactional
    public void deleteAccount(Long accountNumber, Long userId) {
        // 계좌 확인
        Account account = accountRepository.findByAccountNumber(accountNumber).orElseThrow(
                () -> new CustomApiException("존재하지 않는 계좌입니다."));
        // 계좌 소유자 확인
        account.checkOwner(userId);
        // 계좌 잔액 확인
        account.checkBalance();
        // 계좌 삭제
        accountRepository.delete(account);
    }

    @Transactional
    public DepositResponseDto deposit(DepositRequestDto depositRequestDto, Long userId) {
        // 계좌 확인
        Account fromAccount = accountRepository.findByAccountNumber(depositRequestDto.getFromAccountNumber())
                .orElseThrow(() -> new CustomApiException("존재하지 않는 계좌입니다."));
        Account toAccount = accountRepository.findByAccountNumber(depositRequestDto.getToAccountNumber())
                .orElseThrow(() -> new CustomApiException("입금 계좌를 찾을 수 없습니다."));
        if (fromAccount.getAccountNumber().equals(toAccount.getAccountNumber())) {
            throw new CustomApiException("입출금 계좌가 동일합니다.");
        }
        // 계좌 소유자 확인
        fromAccount.checkOwner(userId);
        // 패스워드 확인
        fromAccount.checkPassword(depositRequestDto.getPassword());
        // 잔액 > 입금액 검사
        fromAccount.checkDepositAmount(depositRequestDto.getAmount());
        // 입금
        fromAccount.deposit(depositRequestDto.getAmount());
        toAccount.receive(depositRequestDto.getAmount());
        // 거래내역 남기기 (추가 예정)
        transactionService.saveDepositHistory(fromAccount, toAccount);
        return new DepositResponseDto(fromAccount, toAccount);
    }
}