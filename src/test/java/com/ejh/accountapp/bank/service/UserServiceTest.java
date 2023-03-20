package com.ejh.accountapp.bank.service;

import com.ejh.accountapp.bank.domain.user.User;
import com.ejh.accountapp.bank.domain.user.UserRepository;
import com.ejh.accountapp.bank.domain.user.UserRole;
import com.ejh.accountapp.bank.dto.user.JoinRequestDto;
import com.ejh.accountapp.bank.dto.user.JoinResponseDto;
import com.ejh.accountapp.bank.dto.user.UpdateUserPasswordRequestDto;
import com.ejh.accountapp.bank.dummy.DummyObject;
import com.ejh.accountapp.bank.handler.exception.CustomApiException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Slf4j
class UserServiceTest {
    @InjectMocks
    UserService userService;
    @Mock
    UserRepository userRepository;
    @Spy
    BCryptPasswordEncoder passwordEncoder;

    @Test
    @DisplayName("회원가입 테스트")
    void joinUserTest() {
        // given
        JoinRequestDto joinRequestDto = new JoinRequestDto(
                "ejh", "1234", "e4033jh@daum.net");

        // stub
        User joinUser = User.builder()
                .id(1L)
                .username("ejh")
                .password(passwordEncoder.encode("1234"))
                .email("e4033jh@daum.net")
                .role(UserRole.CUSTOMER)
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .build();
        when(userRepository.save(any())).thenReturn(joinUser);

        // when
        JoinResponseDto joinResponseDto = userService.join(joinRequestDto);

        // then
        assertThat(joinResponseDto.getId()).isEqualTo(1L);
        assertThat(joinResponseDto.getUsername()).isEqualTo("ejh");
        assertThat(joinResponseDto.getEmail()).isEqualTo("e4033jh@daum.net");
    }

    @Test
    @DisplayName("회원가입 중복 테스트")
    void joinDuplicateTest() {
        // given
        User duplicateUser = User.builder()
                .id(1L)
                .username("ejh")
                .password(passwordEncoder.encode("1234"))
                .email("e4033jh@daum.net")
                .role(UserRole.CUSTOMER)
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .build();
        JoinRequestDto joinRequestDto = new JoinRequestDto(
                "ejh", "1234", "e4033jh@daum.net");

        // stub
        when(userRepository.findByUsername(any())).thenReturn(Optional.of(duplicateUser));

        // when

        // then
        Assertions.assertThrows(CustomApiException.class, () -> userService.join(joinRequestDto));
    }

    @Test
    @DisplayName("사용자 패스워드 변경 테스트")
    void updatePasswordTest() throws Exception {
        // given
        User user = DummyObject.createUser("ejh", "e4033jh@daum.net");
        UpdateUserPasswordRequestDto updateUserPasswordRequestDto =
                new UpdateUserPasswordRequestDto("1234", "4321");

        // stub
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        // when
        userService.updateUserPassword(updateUserPasswordRequestDto, user.getId());

        // then
        assertTrue(passwordEncoder.matches(updateUserPasswordRequestDto.getNewPassword(), user.getPassword()));
    }
}