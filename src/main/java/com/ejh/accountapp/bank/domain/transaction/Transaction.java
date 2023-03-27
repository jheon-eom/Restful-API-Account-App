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
    private Account depositAccount;
    @ManyToOne(fetch = FetchType.LAZY)
    private Account receiveAccount;
    @Column(nullable = false)
    private int amount;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DepositType depositType;
    private String sender;
    private String receiver;
    @Column(nullable = false)
    @CreatedDate
    private LocalDateTime createdAt;
    @Column(nullable = false)
    @LastModifiedDate
    private LocalDateTime modifiedAt;

    @Builder
    public Transaction(Long id, Account depositAccount, Account receiveAccount, int amount, DepositType depositType,
                       String sender, String receiver, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.depositAccount = depositAccount;
        this.receiveAccount = receiveAccount;
        this.amount = amount;
        this.depositType = depositType;
        this.sender = sender;
        this.receiver = receiver;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
