package com.ejh.accountapp.bank.domain.account;

import com.ejh.accountapp.bank.domain.user.User;
import com.ejh.accountapp.bank.domain.user.UserRepository;
import com.ejh.accountapp.bank.dto.account.CreateAccountRequestDto;
import com.ejh.accountapp.bank.dummy.DummyObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@Slf4j
@Transactional
class AccountRepositoryTest {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    UserRepository userRepository;

    @AfterEach
    void clear() {
        accountRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("계좌번호 조회 쿼리 테스트")
    void findByAccountNumberTest() throws Exception {
        // given
        CreateAccountRequestDto createAccountRequestDto = new CreateAccountRequestDto(
                1234567890L, 1234L);
        User user = DummyObject.createUser("ejh", "e4033jh@daum.net");
        User accountUser = userRepository.save(user);
        accountRepository.save(createAccountRequestDto.toEntity(accountUser));

        // when
        // 통합 테스트 멱등성 문제 해결해야함
        Account findAccount = accountRepository.findByAccountNumber(
                createAccountRequestDto.getAccountNumber()).get();

        // then
        // fetch join이 잘 되는지 여부 확인
        assertThat(findAccount.getAccountNumber()).isEqualTo(1234567890L);
        assertThat(findAccount.getUser()).isNotNull();
    }

    @Test
    @DisplayName("계좌 목록 조회 쿼리 테스트")
    void findByUserIdTest() throws Exception {
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
        List<Account> accountList = accountRepository.findByUserId(accountUser.getId());

        // then
        assertThat(accountList.size()).isEqualTo(2);
        assertTrue(accountList.get(0).getUser() != null);
    }
}