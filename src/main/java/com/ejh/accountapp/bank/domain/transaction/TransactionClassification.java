package com.ejh.accountapp.bank.domain.transaction;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public enum TransactionClassification {
    DEPOSIT("입금"),
    WITHDRAW("출금"),
    ALL("전체");

    private String value;
}
