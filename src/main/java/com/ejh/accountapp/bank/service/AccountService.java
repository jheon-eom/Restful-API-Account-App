package com.ejh.accountapp.bank.service;

import com.ejh.accountapp.bank.domain.account.Account;
import com.ejh.accountapp.bank.domain.account.AccountRepository;
import com.ejh.accountapp.bank.domain.user.User;
import com.ejh.accountapp.bank.domain.user.UserRepository;
import com.ejh.accountapp.bank.dto.account.AccountDetailRequestDto;
import com.ejh.accountapp.bank.dto.account.AccountDetailResponseDto;
import com.ejh.accountapp.bank.dto.account.CreateAccountRequestDto;
import com.ejh.accountapp.bank.dto.account.CreateAccountResponseDto;
import com.ejh.accountapp.bank.handler.exception.CustomApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

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

    public AccountDetailResponseDto accountDetail(AccountDetailRequestDto accountDetailRequestDto, Long userId) {
        // 계좌 확인
        Account account = accountRepository.findByAccountNumber(accountDetailRequestDto.getAccountNumber())
                .orElseThrow(() -> new CustomApiException("존재하지 않는 계좌입니다."));
        // 계좌 소유자 확인
        account.checkOwner(userId);
        // 계좌 상세 조회
        return new AccountDetailResponseDto(account);
    }
}
