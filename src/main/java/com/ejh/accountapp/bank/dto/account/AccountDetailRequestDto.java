package com.ejh.accountapp.bank.dto.account;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountDetailRequestDto {
    @NotNull
    @Digits(integer = 10, fraction = 10, message = "계좌번호 10자리를 입력해주세요.")
    private Long accountNumber;
    @NotNull
    @Digits(integer = 4, fraction = 4, message = "패스워드 4자리를 입력해주세요.")
    private Long password;
}
