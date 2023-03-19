package com.ejh.accountapp.bank.web;

import com.ejh.accountapp.bank.domain.user.User;
import com.ejh.accountapp.bank.domain.user.UserRepository;
import com.ejh.accountapp.bank.dto.user.JoinRequestDto;
import com.ejh.accountapp.bank.dto.user.UpdateUserPasswordRequest;
import com.ejh.accountapp.bank.dummy.DummyUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Slf4j
@Transactional
class UserApiControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        joinUser();
    }

    @Test
    @DisplayName("회원가입 성공 테스트")
    void joinSuccessTest() throws Exception {
        // given
        JoinRequestDto joinRequestDto = new JoinRequestDto(
                "ejh", "1234", "e4033jh@daum.net");
        String requestBody = objectMapper.writeValueAsString(joinRequestDto);
        log.info("회원가입 요청 = {}", requestBody);

        // when
        ResultActions resultActions = mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON).content(requestBody));
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        log.info("회원가입 응답 = {}", responseBody);

        // then
        resultActions.andExpect(status().isCreated());
    }

    @Test
    @DisplayName("회원가입 실패 테스트")
    void joinFailTest() throws Exception {
        // given
        JoinRequestDto joinRequestDto = new JoinRequestDto(
                "ejh123", "1234", "e123123jh@daum.net");
        String requestBody = objectMapper.writeValueAsString(joinRequestDto);
        log.info("회원가입 요청 = {}", requestBody);

        ResultActions resultActions = mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON).content(requestBody));
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        log.info("회원가입 응답 = {}", responseBody);

        // then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("사용자 패스워드 변경 컨트롤러 테스트")
    @WithUserDetails(value = "ejh123", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void updateUserPasswordTest() throws Exception {
        // given
        UpdateUserPasswordRequest updateUserPasswordRequest =
                new UpdateUserPasswordRequest("1234", "4321");
        String requestBody = objectMapper.writeValueAsString(updateUserPasswordRequest);
        log.info("패스워드 변경 요청 = {}", updateUserPasswordRequest);

        // when
        ResultActions resultActions = mockMvc.perform(put("/api/s/users/password")
                .contentType(MediaType.APPLICATION_JSON).content(requestBody));
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        log.info("패스워드 변경 응답 = {}", responseBody);

        // then
        resultActions.andExpect(status().isOk());
    }

    void joinUser() {
        User user = DummyUser.createUser("ejh123", "ejh123@daum.net");
        userRepository.save(user);
    }
}