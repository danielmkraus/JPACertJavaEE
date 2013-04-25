package com.abank.data.dao.impl;

import com.abank.data.dao.BranchDaoIf;
import com.abank.data.domain.Branch;
import com.abank.data.domain.Employee;
import com.abank.data.producers.BankPersUnit;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by: gruppd, 09.02.13 15:17
 */
public class BranchDao implements BranchDaoIf {

    @Inject
    @BankPersUnit
    private EntityManager em;

    @Override
    public Branch createNew() {
        return new Branch();
    }

    @Override
    public void persist(Branch branch) {
        em.persist(branch);
    }

    @Override
    public void merge(Branch branch) {
        em.merge(branch);
    }

    @Override
    public List<Branch> findAll() {
        List<Branch> retList = em.createNamedQuery(Branch.QUERY_FIND_ALL, Branch.class).getResultList();
        return retList;
    }

    @Override
    public Branch findById(long id) {
        Branch branch = em.createNamedQuery(Branch.QUERY_FIND_BY_ID, Branch.class).getSingleResult();
        return branch;
    }

    @Override
    public void delete(Branch branch) {
        em.remove(branch);
    }
}
