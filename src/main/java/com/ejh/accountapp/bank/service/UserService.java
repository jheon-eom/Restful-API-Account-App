package com.ejh.accountapp.bank.service;

import com.ejh.accountapp.bank.domain.user.User;
import com.ejh.accountapp.bank.domain.user.UserRepository;
import com.ejh.accountapp.bank.dto.user.JoinRequestDto;
import com.ejh.accountapp.bank.dto.user.JoinResponseDto;
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
        User joinUser = userRepository.save(joinRequestDto.toEntity(passwordEncoder));
        return new JoinResponseDto(joinUser);
    }
}
