package com.ejh.accountapp.bank.domain.transaction;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public enum TransactionType {
    ALL("전체"),
    DEPOSIT("입금함"),
    RECEIVE("입금받음");

    private String value;
}
