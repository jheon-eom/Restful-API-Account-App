package com.ejh.accountapp.bank.web;

import com.ejh.accountapp.bank.config.auth.CustomUserDetails;
import com.ejh.accountapp.bank.dto.ResponseDto;
import com.ejh.accountapp.bank.dto.account.CreateAccountRequestDto;
import com.ejh.accountapp.bank.dto.account.CreateAccountResponseDto;
import com.ejh.accountapp.bank.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AccountApiController {
    private final AccountService accountService;

    @PostMapping("/s/accounts")
    public ResponseEntity<?> createAccount(@RequestBody @Valid CreateAccountRequestDto createAccountRequestDto,
                                           BindingResult bindingResult,
                                           @AuthenticationPrincipal CustomUserDetails userDetails) {
        CreateAccountResponseDto createAccountResponseDto = accountService.createAccount(
                createAccountRequestDto, userDetails.getUser().getId());
        return new ResponseEntity<>(new ResponseDto<>(1, "계좌 등록 완료", createAccountResponseDto),
                HttpStatus.CREATED);
    }
}
