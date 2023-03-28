package com.ejh.accountapp.bank.domain.transaction;

import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomTransactionDao {
    List<Transaction> findTransactionList(@Param("accountId") Long accountId, @Param("type") String type,
                                          @Param("page") Integer page);
}
