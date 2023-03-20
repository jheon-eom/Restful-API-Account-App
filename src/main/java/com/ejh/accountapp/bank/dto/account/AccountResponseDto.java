package com.ejh.accountapp.bank.dto.account;

import com.ejh.accountapp.bank.domain.account.Account;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountResponseDto {
    private Long accountNumber;
    private String username;
    private Long balance;

    public AccountResponseDto(Account account) {
        this.accountNumber = account.getAccountNumber();
        this.username = account.getUser().getUsername();
        this.balance = account.getBalance();
    }
}
