package com.ejh.accountapp.bank.web;

import com.ejh.accountapp.bank.config.auth.CustomUserDetails;
import com.ejh.accountapp.bank.dto.ResponseDto;
import com.ejh.accountapp.bank.dto.account.*;
import com.ejh.accountapp.bank.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AccountApiController {
    private final AccountService accountService;

    // 계좌 등록
    @PostMapping("/s/accounts")
    public ResponseEntity<?> createAccount(@RequestBody @Valid CreateAccountRequestDto createAccountRequestDto,
                                           BindingResult bindingResult,
                                           @AuthenticationPrincipal CustomUserDetails userDetails) {
        CreateAccountResponseDto createAccountResponseDto = accountService.createAccount(
                createAccountRequestDto, userDetails.getUser().getId());
        return new ResponseEntity<>(new ResponseDto<>(1, "계좌 등록 완료", createAccountResponseDto),
                HttpStatus.CREATED);
    }

    // 계좌 목록 조회
    @GetMapping("/s/accounts")
    public ResponseEntity<?> getAccountList(@AuthenticationPrincipal CustomUserDetails userDetails) {
        List<AccountResponseDto> accountResponseDtoList = accountService.getAccountList(userDetails.getUser().getId());
        return new ResponseEntity<>(new ResponseDto<>(1, "계좌 목록 조회 성공", accountResponseDtoList),
                HttpStatus.OK);
    }

    // 계좌 상세 조회
    @GetMapping("/s/accounts/detail")
    public ResponseEntity<?> getAccountDetail(@RequestBody @Valid AccountDetailRequestDto accountDetailRequestDto,
                                           BindingResult bindingResult,
                                           @AuthenticationPrincipal CustomUserDetails userDetails) {
        AccountDetailResponseDto accountDetailResponseDto = accountService.getAccountDetail(accountDetailRequestDto,
                userDetails.getUser().getId());
        return new ResponseEntity<>(new ResponseDto<>(1, "계좌 상세 조회 성공", accountDetailResponseDto),
                HttpStatus.OK);
    }

    // 계좌 삭제
    @DeleteMapping("/s/accounts/{accountNumber}")
    public ResponseEntity<?> deleteAccount(@PathVariable("accountNumber") Long accountNumber,
                                           @AuthenticationPrincipal CustomUserDetails userDetails) {
        accountService.deleteAccount(accountNumber, userDetails.getUser().getId());
        return new ResponseEntity<>(new ResponseDto<>(1, "계좌 삭제 성공", null), HttpStatus.OK);
    }

    // 계좌 이체
    @PostMapping("/s/accounts/deposit")
    public ResponseEntity<?> deposit(@RequestBody @Valid DepositRequestDto depositRequestDto,
                                     BindingResult bindingResult,
                                     @AuthenticationPrincipal CustomUserDetails userDetails) {
        DepositResponseDto depositResponseDto = accountService.deposit(depositRequestDto, userDetails.getUser().getId());
        return new ResponseEntity<>(new ResponseDto<>(1, "계좌 입금 완료", depositResponseDto),
                HttpStatus.OK);
    }
}
