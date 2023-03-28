package com.ejh.accountapp.bank.web;

import com.ejh.accountapp.bank.config.auth.CustomUserDetails;
import com.ejh.accountapp.bank.dto.ResponseDto;
import com.ejh.accountapp.bank.dto.transaction.TransactionResponseDto;
import com.ejh.accountapp.bank.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TransactionApiController {
    private final TransactionService transactionService;

    // 거래 목록 조회
    @GetMapping("/s/account/{accountNumber}/transaction/{type}")
    public ResponseEntity<?> findTransactionList(@PathVariable("accountNumber") Long accountNumber,
                                                 @PathVariable("type") String type,
                                                 @RequestParam(value = "page", defaultValue = "0") Integer page,
                                                 @AuthenticationPrincipal CustomUserDetails userDetails) {
        List<TransactionResponseDto> transactionResponseDtoList = transactionService.findTransactionList(
                accountNumber, type, userDetails.getUser().getId(), page);
        return new ResponseEntity<>(new ResponseDto<>(1, "거래 목록 조회 성공", transactionResponseDtoList),
                HttpStatus.OK);
    }
}
