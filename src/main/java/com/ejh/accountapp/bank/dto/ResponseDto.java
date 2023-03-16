package com.ejh.accountapp.bank.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ResponseDto<T> {
    private int code;
    private String msg;
    private T data;
}
