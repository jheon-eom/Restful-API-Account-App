package com.ejh.accountapp.bank.domain.user;

import com.ejh.accountapp.bank.dto.user.JoinRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void saveUser() {
        User user = User.builder()
                .id(1L)
                .username("ejh")
                .password("1234")
                .email("e4033jh@daum.net")
                .role(UserRole.CUSTOMER)
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .build();
        userRepository.save(user);
    }

    @Test
    @DisplayName("이름으로 조회 테스트")
    void findByUserNameTest() {
        // given
        JoinRequestDto joinRequestDto = new JoinRequestDto("ejh", "1234", "e4033jh@daum.net");

        // when
        Optional<User> findUser = userRepository.findByUsername(joinRequestDto.getUsername());

        // then
        assertTrue(findUser.isPresent());
    }
}