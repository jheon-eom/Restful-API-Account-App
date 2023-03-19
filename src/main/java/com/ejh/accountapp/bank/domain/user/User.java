package com.ejh.accountapp.bank.domain.user;

import com.ejh.accountapp.bank.handler.exception.CustomApiException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EntityListeners(AuditingEntityListener.class)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true, length = 20)
    private String username;
    @Column(nullable = false, length = 80)
    private String password;
    @Column(nullable = false, unique = true, length = 20)
    private String email;
    @Column(nullable = false)
    @Enumerated
    private UserRole role;
    @Column(nullable = false)
    @CreatedDate
    private LocalDateTime createdAt;
    @Column(nullable = false)
    @LastModifiedDate
    private LocalDateTime modifiedAt;

    @Builder
    public User(Long id, String username, String password, String email, UserRole role,
                LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public void updatePassword(String currentPassword, String newPassword,
                               BCryptPasswordEncoder passwordEncoder) {
        verifyPassword(currentPassword, passwordEncoder);
        checkSamePassword(newPassword, passwordEncoder);
        password = passwordEncoder.encode(newPassword);
    }

    private void verifyPassword(String currentPassword, BCryptPasswordEncoder passwordEncoder) {
        if (!passwordEncoder.matches(currentPassword, password)) {
            throw new CustomApiException("패스워드가 일치하지 않습니다.");
        }
    }

    private void checkSamePassword(String newPassword, BCryptPasswordEncoder passwordEncoder) {
        if (passwordEncoder.matches(newPassword, password)) {
            throw new CustomApiException("현재 패스워드와 동일합니다.");
        }
    }
}
