package com.ejh.accountapp.bank.web;

import com.ejh.accountapp.bank.config.auth.CustomUserDetails;
import com.ejh.accountapp.bank.dto.ResponseDto;
import com.ejh.accountapp.bank.dto.account.AccountDetailRequestDto;
import com.ejh.accountapp.bank.dto.account.AccountDetailResponseDto;
import com.ejh.accountapp.bank.dto.account.CreateAccountRequestDto;
import com.ejh.accountapp.bank.dto.account.CreateAccountResponseDto;
import com.ejh.accountapp.bank.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    // 계좌 상세 조회
    @GetMapping("/s/accounts/detail")
    public ResponseEntity<?> accountDetail(@RequestBody @Valid AccountDetailRequestDto accountDetailRequestDto,
                                           BindingResult bindingResult,
                                           @AuthenticationPrincipal CustomUserDetails userDetails) {
        AccountDetailResponseDto accountDetailResponseDto = accountService.accountDetail(accountDetailRequestDto,
                userDetails.getUser().getId());
        return new ResponseEntity<>(new ResponseDto<>(1, "계좌 상세 조회 성공", accountDetailResponseDto),
                HttpStatus.OK);
    }
}
