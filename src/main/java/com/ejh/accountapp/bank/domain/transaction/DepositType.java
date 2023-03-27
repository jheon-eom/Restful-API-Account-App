package com.ejh.accountapp.bank.domain.transaction;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public enum DepositType {
    DEBT("빚"),
    DUTCH("더치페이"),
    POCKET("용돈");

    private String value;
}
