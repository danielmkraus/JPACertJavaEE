package com.abank.data.dao.impl;

import com.abank.data.dao.EmployeeDaoIf;
import com.abank.data.domain.Employee;
import com.abank.data.producers.BankPersUnit;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by: gruppd, 09.02.13 15:55
 */
public class EmployeeDao implements EmployeeDaoIf {

    @Inject
    @BankPersUnit
    private EntityManager em;

    @Override
    public Employee createNew() {
        return new Employee();
    }

    @Override
    public void merge(Employee employee) {
        em.merge(employee);
    }

    @Override
    public void persist(Employee employee) {
        em.persist(employee);
    }

    @Override
    public List<Employee> findAll() {
        List<Employee> retList = em.createNamedQuery(Employee.QUERY_FIND_ALL, Employee.class).getResultList();
        return retList;
    }

    @Override
    public Employee findById(long id) {
        Employee employee = em.createNamedQuery(Employee.QUERY_FIND_BY_ID, Employee.class).getSingleResult();
        return employee;
    }

    @Override
    public void delete(Employee employee) {
        em.remove(employee);
    }
}
