package com.ejh.accountapp.bank.config.jwt;

import com.ejh.accountapp.bank.domain.user.User;
import com.ejh.accountapp.bank.domain.user.UserRepository;
import com.ejh.accountapp.bank.domain.user.UserRole;
import com.ejh.accountapp.bank.dto.user.LoginRequestDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
//@AutoConfigureMockMvc
class JwtAuthenticationFilterTest {
    private final Logger log = LoggerFactory.getLogger(getClass());
//    @Autowired
//    MockMvc mockMvc;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    void jwtLogin_test() throws JsonProcessingException {
        // given
        userRepository.save(createTestUser(UserRole.CUSTOMER));
        LoginRequestDto loginRequestDto = new LoginRequestDto("ejh", "1234");
        String requestBody = objectMapper.writeValueAsString(loginRequestDto);
    }

    User createTestUser(UserRole role) {
        UserRole userRole = role;
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return User.builder()
                .username("ejh")
                .password(passwordEncoder.encode("1234"))
                .email("e4033jh@daum.net")
                .role(userRole)
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .build();
    }
}