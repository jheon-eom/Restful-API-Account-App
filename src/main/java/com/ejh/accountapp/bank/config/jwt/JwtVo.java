package com.ejh.accountapp.bank.config.jwt;

/**
 * Jwt 프로세스를 위해 정의된 클래스
 * 상속 금지
 */
public final class JwtVo {
    public static final String SECRET = "ejhToken";
    public static final int EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 7; // 일주일
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER = "Authorization";
}
