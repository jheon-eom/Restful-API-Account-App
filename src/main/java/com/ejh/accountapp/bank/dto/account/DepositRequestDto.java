package com.ejh.accountapp.bank.dto.account;

import lombok.*;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DepositRequestDto {
    @NotNull
    @Digits(integer = 10, fraction = 10, message = "계좌번호 10자리를 입력해주세요.")
    private Long fromAccountNumber;
    @NotNull
    @Digits(integer = 10, fraction = 10, message = "계좌번호 10자리를 입력해주세요.")
    private Long toAccountNumber;
    @NotNull
    @Digits(integer = 4, fraction = 4, message = "패스워드 4자리를 입력해주세요.")
    private Long password;
    @NotNull
    private Long amount;

    @Builder
    public DepositRequestDto(Long fromAccountNumber, Long toAccountNumber, Long password, Long amount) {
        this.fromAccountNumber = fromAccountNumber;
        this.toAccountNumber = toAccountNumber;
        this.password = password;
        this.amount = amount;
    }
}
