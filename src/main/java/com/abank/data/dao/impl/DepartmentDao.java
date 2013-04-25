package com.abank.data.dao.impl;

import com.abank.data.dao.DepartmentDaoIf;
import com.abank.data.domain.Customer;
import com.abank.data.domain.Department;
import com.abank.data.producers.BankPersUnit;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by: gruppd, 09.02.13 15:55
 */
public class DepartmentDao implements DepartmentDaoIf {

    @Inject
    @BankPersUnit
    private EntityManager em;

    @Override
    public Department createNew() {
        return new Department();
    }

    @Override
    public void persist(Department department) {
        em.persist(department);
    }

    @Override
    public void merge(Department department) {
        em.merge(department);
    }

    @Override
    public List<Department> findAll() {
        List<Department> retList = em.createNamedQuery(Department.QUERY_FIND_ALL, Department.class).getResultList();
        return retList;
    }

    @Override
    public Department findById(long id) {
        Department department = em.createNamedQuery(Department.QUERY_FIND_BY_ID, Department.class).getSingleResult();
        return department;
    }

    @Override
    public void delete(Department department) {
        em.remove(department);
    }
}
