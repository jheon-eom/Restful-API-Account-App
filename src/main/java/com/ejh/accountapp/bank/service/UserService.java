package com.ejh.accountapp.bank.service;

import com.ejh.accountapp.bank.domain.user.User;
import com.ejh.accountapp.bank.domain.user.UserRepository;
import com.ejh.accountapp.bank.dto.user.JoinRequestDto;
import com.ejh.accountapp.bank.dto.user.JoinResponseDto;
import com.ejh.accountapp.bank.dto.user.UpdateUserPasswordRequestDto;
import com.ejh.accountapp.bank.handler.exception.CustomApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public JoinResponseDto join(JoinRequestDto joinRequestDto) {
        // 사용자 중복 검사
        Optional<User> duplicateUser = userRepository.findByUsername(joinRequestDto.getUsername());
        if (duplicateUser.isPresent()) {
            throw new CustomApiException("동일한 이름의 사용자가 존재합니다.");
        }
        // 회원가입
        User joinUser = userRepository.save(joinRequestDto.toEntity(passwordEncoder));
        return new JoinResponseDto(joinUser);
    }

    @Transactional
    public void updateUserPassword(UpdateUserPasswordRequestDto updateUserPasswordRequestDto, Long userId) {
        // 사용자 확인
        User user = userRepository.findById(userId).orElseThrow(
                () -> new CustomApiException("존재하지 않는 사용자입니다."));
        // 패스워드 변경
        user.updatePassword(updateUserPasswordRequestDto.getCurrentPassword(),
                updateUserPasswordRequestDto.getNewPassword(), passwordEncoder);
    }
}
