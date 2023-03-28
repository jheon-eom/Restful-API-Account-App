package com.ejh.accountapp.bank.dto.transaction;

import com.ejh.accountapp.bank.domain.transaction.Transaction;
import com.ejh.accountapp.bank.util.CommonDateUtil;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TransactionResponseDto {
    private Long id;
    private String transactionType;
    private Long amount;
    private String sender;
    private String receiver;
    private String transactionTime;

    @Builder
    public TransactionResponseDto(Transaction transaction) {
        this.id = transaction.getId();
        this.transactionType = transaction.getTransactionType().getValue();
        this.amount = transaction.getAmount();
        this.sender = transaction.getSender();
        this.receiver = transaction.getReceiver();
        this.transactionTime = CommonDateUtil.toStringFormat(transaction.getCreatedAt());
    }
}
