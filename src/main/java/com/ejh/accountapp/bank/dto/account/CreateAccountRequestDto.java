package com.ejh.accountapp.bank.dto.account;

import com.ejh.accountapp.bank.domain.account.Account;
import com.ejh.accountapp.bank.domain.user.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateAccountRequestDto {
    @NotNull
    @Digits(integer = 10, fraction = 10, message = "계좌번호 10자리를 입력해주세요.")
    private Long accountNumber; // 추후에 자동으로 발급되도록 기능 수정
    @NotNull
    @Digits(integer = 4, fraction = 4, message = "패스워드 4자리를 입력해주세요.")
    private Long password;

    public Account toEntity(User user) {
        return Account.builder()
                .accountNumber(accountNumber)
                .password(password)
                .balance(1000L)
                .user(user)
                .build();
    }
}