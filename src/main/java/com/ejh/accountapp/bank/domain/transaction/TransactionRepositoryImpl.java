package com.ejh.accountapp.bank.domain.transaction;

import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@RequiredArgsConstructor
public class TransactionRepositoryImpl implements CustomTransactionDao {
    private final EntityManager entityManager;

    @Override
    public List<Transaction> findTransactionList(Long accountId, String type, Integer page) {
        String sql = "";
        sql += "select t from Transaction t ";
        if (type.equals("DEPOSIT")) {
            sql += "join fetch t.depositAccount da ";
            sql += "where t.depositAccount.id = :depositAccountId";
        } else if (type.equals("RECEIVE")) {
            sql += "join fetch t.receiveAccount ra ";
            sql += "where t.receiveAccount.id = :receiveAccountId ";
        } else {
            sql += "left join fetch t.depositAccount da ";
            sql += "left join fetch t.receiveAccount ra ";
            sql += "where t.depositAccount.id = :depositAccountId ";
            sql += "or ";
            sql += "t.receiveAccount.id = :receiveAccountId ";
        }
        sql += "order by t.createdAt desc";
        TypedQuery<Transaction> query = entityManager.createQuery(sql, Transaction.class);
        if (type.equals("DEPOSIT")) {
            query = query.setParameter("depositAccountId", accountId);
        } else if (type.equals("RECEIVE")) {
            query = query.setParameter("receiveAccountId", accountId);
        } else {
            query = query.setParameter("depositAccountId", accountId);
            query = query.setParameter("receiveAccountId", accountId);
        }
        query.setFirstResult(page * 10);
        query.setMaxResults(10);
        return query.getResultList();
    }
}
