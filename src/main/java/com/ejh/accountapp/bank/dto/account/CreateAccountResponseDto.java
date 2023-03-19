package com.ejh.accountapp.bank.dto.account;

import com.ejh.accountapp.bank.domain.account.Account;
import lombok.Getter;

@Getter
public class CreateAccountResponseDto {
    private Long id;
    private Long accountNumber;
    private Long balance;

    public CreateAccountResponseDto(Account account) {
        this.id = account.getId();
        this.accountNumber = account.getAccountNumber();
        this.balance = account.getBalance();
    }
}
