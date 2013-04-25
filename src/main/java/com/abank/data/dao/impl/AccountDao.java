package com.abank.data.dao.impl;

import com.abank.data.dao.AccountDaoIf;
import com.abank.data.domain.Account;
import com.abank.data.producers.BankPersUnit;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by: gruppd, 09.02.13 15:55
 */
public class AccountDao implements AccountDaoIf {

    @Inject
    @BankPersUnit
    private EntityManager em;

    @Override
    public Account createNew() {
        return new Account();
    }

    @Override
    public void persist(Account account) {
        em.persist(account);
    }

    @Override
    public void merge(Account account) {
        em.merge(account);
    }

    @Override
    public List<Account> findAll() {
        List<Account> retList = em.createNamedQuery(Account.QUERY_FIND_ALL, Account.class).getResultList();
        return retList;
    }

    @Override
    public Account findById(long id) {
        Account account = em.createNamedQuery(Account.QUERY_FIND_BY_ID, Account.class).getSingleResult();
        return account;
    }

    @Override
    public void delete(Account account) {
        em.remove(account);
    }
}
