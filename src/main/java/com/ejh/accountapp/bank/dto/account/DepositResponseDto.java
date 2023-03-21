package com.ejh.accountapp.bank.dto.account;

import com.ejh.accountapp.bank.domain.account.Account;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DepositResponseDto {
    private Long fromAccountNumber;
    private Long toAccountNumber;
    private String fromUsername;
    private String toUsername;
    private Long balance;

    public DepositResponseDto(Account fromAccount, Account toAccount) {
        this.fromAccountNumber = fromAccount.getAccountNumber();
        this.toAccountNumber = toAccount.getAccountNumber();
        this.fromUsername = fromAccount.getUser().getUsername();
        this.toUsername = toAccount.getUser().getUsername();
        this.balance = fromAccount.getBalance();
    }
}
