package com.ejh.accountapp.bank.config.jwt;

import com.ejh.accountapp.bank.domain.user.User;
import com.ejh.accountapp.bank.domain.user.UserRepository;
import com.ejh.accountapp.bank.domain.user.UserRole;
import com.ejh.accountapp.bank.dto.user.LoginRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Slf4j
@Transactional
class JwtAuthenticationFilterTest {
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    UserRepository userRepository;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    void testJoinUser() {
        User user = User.builder()
                .id(1L)
                .username("ejh")
                .password(passwordEncoder.encode("1234"))
                .email("e4033jh@daum.net")
                .role(UserRole.CUSTOMER)
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .build();
        userRepository.save(user);
    }

    @Test
    @DisplayName("로그인 성공 테스트")
    void LoginSuccessTest() throws Exception {
        // given
        LoginRequestDto loginRequestDto = new LoginRequestDto("ejh", "1234");
        String requestBody = objectMapper.writeValueAsString(loginRequestDto);
        log.info("로그인 요청 = {}", requestBody);

        // when
        ResultActions resultActions = mockMvc.perform(post("/api/login")
                .contentType(MediaType.APPLICATION_JSON).content(requestBody));
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        log.info("로그인 요청 응답 = {}", responseBody);
        String token = resultActions.andReturn().getResponse().getHeader("Authorization");
        log.info("jwt 토큰 발급 = {}", token);

        // then
        resultActions.andExpect(status().isOk());
        assertNotNull(token);
        assertTrue(token.startsWith("Bearer"));
        resultActions.andExpect(jsonPath("$.data.username").value("ejh"));
    }

    @Test
    @DisplayName("로그인 실패 테스트")
    void loginFailTest() throws Exception {
        // given
        LoginRequestDto loginRequestDto = new LoginRequestDto("ejh", "1111");
        String requestBody = objectMapper.writeValueAsString(loginRequestDto);
        log.info("로그인 요청 = {}", requestBody);

        // when
        ResultActions resultActions = mockMvc.perform(post("/api/login")
                .contentType("application/json; charset=utf-8").content(requestBody));
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        log.info("로그인 요청 응답 = {}", responseBody);
        String token = resultActions.andReturn().getResponse().getHeader("Authorization");
        log.info("jwt 토큰 발급 = {}", token);

        // then
        resultActions.andExpect(status().isUnauthorized());
    }
}