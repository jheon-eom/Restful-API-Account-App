package com.ejh.accountapp.bank.config.jwt;

import com.ejh.accountapp.bank.config.auth.CustomUserDetails;
import com.ejh.accountapp.bank.domain.user.User;
import com.ejh.accountapp.bank.domain.user.UserRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class JwtAuthorizationFilterTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("일반 사용자 토큰 인가 테스트")
    void customerTokenAuthorizationTest() throws Exception {
        // given
        User user = User.builder()
                .id(1L)
                .role(UserRole.CUSTOMER)
                .build();
        CustomUserDetails userDetails = new CustomUserDetails(user);
        String token = JwtProcess.create(userDetails);

        // when
        ResultActions resultActionsNone = mockMvc.perform(get("/api/s/test"));
        ResultActions resultActionsCustom = mockMvc.perform(get("/api/s/test")
                .header(JwtVo.HEADER, token));
        ResultActions resultActionsAdmin = mockMvc.perform(get("/api/admin/test")
                .header(JwtVo.HEADER, token));

        // then
        resultActionsNone.andExpect(status().isUnauthorized());
        resultActionsCustom.andExpect(status().isNotFound());
        resultActionsAdmin.andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("관리자 토큰 인가 테스트")
    void adminTokenAuthorizationTest() throws Exception {
        // given
        User user = User.builder()
                .id(1L)
                .role(UserRole.ADMIN)
                .build();
        CustomUserDetails userDetails = new CustomUserDetails(user);
        String token = JwtProcess.create(userDetails);

        // when
        ResultActions resultActionsAdmin = mockMvc.perform(get("/api/admin/test")
                .header(JwtVo.HEADER, token));

        // then
        resultActionsAdmin.andExpect(status().isNotFound());
    }
}