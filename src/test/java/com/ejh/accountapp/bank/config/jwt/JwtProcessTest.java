package com.ejh.accountapp.bank.config.jwt;

import com.ejh.accountapp.bank.config.auth.CustomUserDetails;
import com.ejh.accountapp.bank.domain.user.User;
import com.ejh.accountapp.bank.domain.user.UserRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class JwtProcessTest {

    @Test
    @DisplayName("토큰 생성 테스트")
    void jwtTokenCreateTest() {
        // given
        String token = createToken();

        // when

        // then
        assertNotNull(token);
    }

    @Test
    @DisplayName("토큰 검증 테스트")
    void verifyTokenTest() {
        // given
        String token = createToken().replace(JwtVo.TOKEN_PREFIX, "");

        // when
        CustomUserDetails userDetails = JwtProcess.verify(token);

        // then
        assertThat(userDetails.getUser().getId()).isEqualTo(1L);
        assertThat(userDetails.getUser().getRole()).isEqualTo(UserRole.ADMIN);
    }

    private String createToken() {
        User user = User.builder()
                .id(1L)
                .role(UserRole.ADMIN)
                .build();
        CustomUserDetails customUserDetails = new CustomUserDetails(user);
        String token = JwtProcess.create(customUserDetails);
        return token;
    }
}