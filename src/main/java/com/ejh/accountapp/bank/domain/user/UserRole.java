package com.ejh.accountapp.bank.domain.user;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public enum UserRole {
    ADMIN("관리자"),
    CUSTOMER("고객");

    private String value;
}
