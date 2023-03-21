package com.ejh.accountapp.bank.domain.account;

import com.ejh.accountapp.bank.domain.user.User;
import com.ejh.accountapp.bank.handler.exception.CustomApiException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_account")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false, length = 20)
    private Long accountNumber;
    @Column(nullable = false, length = 4)
    private Long password;
    @Column(nullable = false)
    private Long balance;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @Column(nullable = false)
    @CreatedDate
    private LocalDateTime createdAt;
    @Column(nullable = false)
    @LastModifiedDate
    private LocalDateTime modifiedAt;

    @Builder
    public Account(Long id, Long accountNumber, Long password, Long balance, User user,
                   LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.password = password;
        this.balance = balance;
        this.user = user;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public void checkOwner(Long userId) {
        if (!user.getId().equals(userId)) {
            throw new CustomApiException("계좌의 소유자가 일치하지 않습니다.");
        }
    }

    public void checkBalance() {
        if (balance > 0L) {
            throw new CustomApiException("계좌에 잔액이 존재합니다.");
        }
    }

    public void checkDepositAmount(Long amount) {
        if (balance < amount) {
            throw new CustomApiException("계좌의 잔액이 부족합니다.");
        }
    }

    public void checkPassword(Long password) {
        if (!this.password.equals(password)) {
            throw new CustomApiException("패스워드가 일치하지 않습니다.");
        }
    }

    public void withdraw(Long amount) {
        this.balance += amount;
    }

    public void deposit(Long amount) {
        this.balance -= amount;
    }
}
