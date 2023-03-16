package com.ejh.accountapp.bank.domain.transaction;

import com.ejh.accountapp.bank.domain.account.Account;
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
@Table(name = "tb_transaction")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Account depositAccount; // 입금 계좌
    @ManyToOne(fetch = FetchType.LAZY)
    private Account withdrawAccount; // 출금 계좌
    @Column(nullable = false)
    private int amount;
    private int withdrawAccountBalance;
    private int depositAccountBalance;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionClassification transactionClassification;
    private String sender; // 입금인
    private String receiver; // 송금인
    @Column(nullable = false)
    @CreatedDate
    private LocalDateTime createdAt;
    @Column(nullable = false)
    @LastModifiedDate
    private LocalDateTime modifiedAt;

    @Builder
    public Transaction(Long id, Account depositAccount, Account withdrawAccount, int amount,
                       int withdrawAccountBalance, int depositAccountBalance, TransactionClassification transactionClassification,
                       String sender, String receiver, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.depositAccount = depositAccount;
        this.withdrawAccount = withdrawAccount;
        this.amount = amount;
        this.withdrawAccountBalance = withdrawAccountBalance;
        this.depositAccountBalance = depositAccountBalance;
        this.transactionClassification = transactionClassification;
        this.sender = sender;
        this.receiver = receiver;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
