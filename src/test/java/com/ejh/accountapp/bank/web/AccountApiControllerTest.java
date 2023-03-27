package com.ejh.accountapp.bank.web;

import com.ejh.accountapp.bank.domain.account.Account;
import com.ejh.accountapp.bank.domain.account.AccountRepository;
import com.ejh.accountapp.bank.domain.user.User;
import com.ejh.accountapp.bank.domain.user.UserRepository;
import com.ejh.accountapp.bank.dto.account.AccountDetailRequestDto;
import com.ejh.accountapp.bank.dto.account.CreateAccountRequestDto;
import com.ejh.accountapp.bank.dto.account.DepositRequestDto;
import com.ejh.accountapp.bank.dummy.DummyObject;
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
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Slf4j
@Transactional
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
        User user = DummyObject.createUser("ejh", "e4033jh@daum.net");
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

    @Test
    @DisplayName("계좌 상세 조회 컨트롤러 테스트")
    @WithUserDetails(value = "ejh", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void accountDetailTest() throws Exception {
        // given
        User user = DummyObject.createUser("ejh", "e4033jh@daum.net");
        userRepository.save(user);
        Account account = DummyObject.createAccount(1L, 1234567890L, 1000L, user);
        accountRepository.save(account);
        AccountDetailRequestDto accountDetailRequestDto = new AccountDetailRequestDto(
                1234567890L, 1234L);
        String requestBody = objectMapper.writeValueAsString(accountDetailRequestDto);
        log.info("요청 = {}", requestBody);

        // when
        ResultActions resultActions = mockMvc.perform(get("/api/s/accounts")
                .contentType(MediaType.APPLICATION_JSON).content(requestBody));
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        log.info("응답 = {}", responseBody);

        // then
        resultActions.andExpect(status().isOk());
    }

    @Test
    @DisplayName("계좌 목록 조회 api 테스트")
    @WithUserDetails(value = "ejh", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void getAccountList() throws Exception {
        // given
        CreateAccountRequestDto createAccountRequestDto1 = new CreateAccountRequestDto(
                1234567890L, 1234L);
        CreateAccountRequestDto createAccountRequestDto2 = new CreateAccountRequestDto(
                1234567891L, 1234L);
        User user = DummyObject.createUser("ejh", "e4033jh@daum.net");
        User accountUser = userRepository.save(user);
        accountRepository.save(createAccountRequestDto1.toEntity(accountUser));
        accountRepository.save(createAccountRequestDto2.toEntity(accountUser));

        // when
        ResultActions resultActions = mockMvc.perform(get("/api/s/accounts"));
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        log.info("응답 = {}", responseBody);

        // then
        resultActions.andExpect(status().isOk());
    }

    @Test
    @DisplayName("계좌 삭제 api 테스트")
    @WithUserDetails(value = "ejh", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void deleteAccountTest() throws Exception {
        // given
        CreateAccountRequestDto createAccountRequestDto1 = new CreateAccountRequestDto(
                1234567890L, 1234L);
        CreateAccountRequestDto createAccountRequestDto2 = new CreateAccountRequestDto(
                1234567891L, 1234L);
        User user = DummyObject.createUser("ejh", "e4033jh@daum.net");
        User accountUser = userRepository.save(user);
        accountRepository.save(createAccountRequestDto1.toEntity(accountUser));
        accountRepository.save(createAccountRequestDto2.toEntity(accountUser));

        // when
        ResultActions resultActions = mockMvc.perform(delete("/api/s/accounts/" + 1234567890L));
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        log.info("응답 = {}", responseBody);

        // then
        resultActions.andExpect(status().isOk());
    }

    @Test
    @DisplayName("계좌 입금 테스트")
    @WithUserDetails(value = "ejh", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void depositTest() throws Exception {
        // given
        CreateAccountRequestDto createAccountRequestDto1 = new CreateAccountRequestDto(
                1234567890L, 1234L);
        CreateAccountRequestDto createAccountRequestDto2 = new CreateAccountRequestDto(
                1234567891L, 1234L);
        User user1 = DummyObject.createUser("ejh", "e4033jh@daum.net");
        User accountUser1 = userRepository.save(user1);
        User user2 = DummyObject.createUser("ejh", "e4033jh@daum.net");
        User accountUser2 = userRepository.save(user2);
        accountRepository.save(createAccountRequestDto1.toEntity(accountUser1));
        accountRepository.save(createAccountRequestDto2.toEntity(accountUser2));
        DepositRequestDto depositRequestDto = DepositRequestDto.builder()
                .fromAccountNumber(1234567890L)
                .toAccountNumber(1234567891L)
                .password(1234L)
                .amount(100L)
                .build();
        String requestBody = objectMapper.writeValueAsString(depositRequestDto);
        log.debug("요청 = {}", requestBody);

        // when
        ResultActions resultActions = mockMvc.perform(post("/api/s/accounts/deposit")
                .contentType(MediaType.APPLICATION_JSON).content(requestBody));
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        log.debug("응답 = {}", responseBody);

        // then
        resultActions.andExpect(status().isOk());
    }
}