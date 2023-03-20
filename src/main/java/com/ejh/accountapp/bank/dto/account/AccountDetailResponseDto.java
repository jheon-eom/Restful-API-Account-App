package com.ejh.accountapp.bank.dto.account;

import com.ejh.accountapp.bank.domain.account.Account;
import lombok.Getter;

@Getter
public class AccountDetailResponseDto {
    private Long accountNumber;
    private String owner;
    private Long balance;

    public AccountDetailResponseDto(Account account) {
        this.accountNumber = account.getAccountNumber();
        this.owner = account.getUser().getUsername();
        this.balance = account.getBalance();
    }
}
