package com.ejh.accountapp.bank.service;

import com.ejh.accountapp.bank.domain.account.Account;
import com.ejh.accountapp.bank.domain.account.AccountRepository;
import com.ejh.accountapp.bank.domain.user.User;
import com.ejh.accountapp.bank.domain.user.UserRepository;
import com.ejh.accountapp.bank.dto.account.CreateAccountRequestDto;
import com.ejh.accountapp.bank.dto.account.CreateAccountResponseDto;
import com.ejh.accountapp.bank.dummy.DummyObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Slf4j
class AccountServiceTest {
    @InjectMocks
    AccountService accountService;
    @Mock
    AccountRepository accountRepository;
    @Mock
    UserRepository userRepository;

    @AfterEach
    void clear() {
        accountRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("계좌 등록 서비스 테스트")
    void createAccountTest() throws Exception {
        // given
        CreateAccountRequestDto createAccountRequestDto = new CreateAccountRequestDto(
                1234567890L, 1234L);
        User user = DummyObject.createUser("ejh", "e4033jh@daum.net");
        Account account = createAccountRequestDto.toEntity(user);

        // stub
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(accountRepository.findByAccountNumber(createAccountRequestDto.getAccountNumber()))
                .thenReturn(Optional.empty());
        when(accountRepository.save(any())).thenReturn(account);

        // when
        CreateAccountResponseDto createAccountResponseDto = accountService.createAccount(
                createAccountRequestDto, 1L);

        // then
        assertThat(createAccountResponseDto.getAccountNumber()).isEqualTo(1234567890L);
    }

    @Test
    @DisplayName("계좌 상세 조회 서비스 테스트")
    void accountDetailTest() throws Exception {
        // given


        // when

        // then
    }
}