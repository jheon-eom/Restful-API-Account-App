package com.ejh.accountapp.bank.config.jwt;

public final class JwtVo {
    public static final String SECRET = "ejhToken";
    public static final int EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 7;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER = "Authorization";
}
