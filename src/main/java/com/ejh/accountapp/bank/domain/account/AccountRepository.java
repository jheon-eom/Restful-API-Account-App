package com.ejh.accountapp.bank.domain.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    @Query("select a from Account a join fetch a.user u where a.accountNumber = :accountNumber")
    Optional<Account> findByAccountNumber(@Param("accountNumber") Long accountNumber);
}
