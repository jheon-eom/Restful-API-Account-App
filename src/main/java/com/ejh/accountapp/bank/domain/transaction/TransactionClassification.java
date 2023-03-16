package com.ejh.accountapp.bank.domain.transaction;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TransactionClassification {
    DEPOSIT("입금"),
    WITHDRAW("출금"),
    ALL("전체");

    private String value;
}
