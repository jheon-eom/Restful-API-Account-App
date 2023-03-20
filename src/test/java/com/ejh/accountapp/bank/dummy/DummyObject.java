package com.ejh.accountapp.bank.dummy;

import com.ejh.accountapp.bank.domain.account.Account;
import com.ejh.accountapp.bank.domain.user.User;
import com.ejh.accountapp.bank.domain.user.UserRole;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;

public class DummyObject {
    static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static User createUser(String username, String email) {
        return User.builder()
                .id(1L)
                .username(username)
                .password(passwordEncoder.encode("1234"))
                .email(email)
                .role(UserRole.CUSTOMER)
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .build();
    }

    public static User createAdminUser(String username, String email) {
        return User.builder()
                .id(2L)
                .username(username)
                .password(passwordEncoder.encode("1234"))
                .email(email)
                .role(UserRole.ADMIN)
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .build();
    }

    public static Account createAccount(Long id, Long accountNumber, Long balance, User user) {
        Account account = Account.builder()
                .id(id)
                .accountNumber(accountNumber)
                .password(1234L)
                .balance(balance)
                .user(user)
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .build();
        return account;
    }
}
