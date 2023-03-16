package com.ejh.accountapp.bank.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ejh.accountapp.bank.config.auth.CustomUserDetails;
import com.ejh.accountapp.bank.domain.user.User;
import com.ejh.accountapp.bank.domain.user.UserRole;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public class JwtProcess {

    // 토큰 생성
    public static String create(CustomUserDetails userDetails) {
        log.debug("debug : create jwt token");
        String jwtToken = JWT.create()
                .withSubject(userDetails.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtVo.EXPIRATION_TIME))
                .withClaim("id", userDetails.getUser().getId())
                .withClaim("role", userDetails.getUser().getRole().name())
                .sign(Algorithm.HMAC512(JwtVo.SECRET));
        return JwtVo.TOKEN_PREFIX + jwtToken;
    }

    // 토큰 검증
    public static CustomUserDetails verify(String token) {
        log.debug("debug : verify jwt token");
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(JwtVo.SECRET)).build().verify(token);
        Long id = decodedJWT.getClaim("id").asLong();
        String role = decodedJWT.getClaim("role").asString();
        User user = User.builder()
                .id(id)
                .role(UserRole.valueOf(role))
                .build();
        CustomUserDetails userDetails = new CustomUserDetails(user);
        return userDetails;
    }
}
