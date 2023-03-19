package com.ejh.accountapp.bank.web;

import com.ejh.accountapp.bank.domain.account.AccountRepository;
import com.ejh.accountapp.bank.domain.user.User;
import com.ejh.accountapp.bank.domain.user.UserRepository;
import com.ejh.accountapp.bank.dto.account.CreateAccountRequestDto;
import com.ejh.accountapp.bank.dummy.DummyUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Slf4j
class AccountApiControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    UserRepository userRepository;
    @Autowired
    AccountRepository accountRepository;

    @BeforeEach
    void setUp() {
        User user = DummyUser.createUser("ejh", "e4033jh@daum.net");
        userRepository.save(user);
    }

    @AfterEach
    void clear() {
        accountRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("계좌 등록 컨트롤러 테스트")
    @WithUserDetails(value = "ejh", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void createAccountTest() throws Exception {
        // given
        CreateAccountRequestDto createAccountRequestDto = new CreateAccountRequestDto(
                1234567819L, 1234L);
        String requestBody = objectMapper.writeValueAsString(createAccountRequestDto);
        log.info("요청 = {}", requestBody);

        // when
        ResultActions resultActions = mockMvc.perform(post("/api/s/accounts")
                .contentType(MediaType.APPLICATION_JSON).content(requestBody));
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        log.info("응답 = {}", responseBody);

        // then
        resultActions.andExpect(status().isCreated());
    }
}