package com.ejh.accountapp.bank.transaction.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TransactionDivision {
    DEPOSIT("입금"),
    WITHDRAW("출금"),
    ALL("전체");

    private String value;
}
