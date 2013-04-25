package com.abank.data.dao.impl;

import com.abank.data.dao.CustomerDaoIf;
import com.abank.data.domain.Branch;
import com.abank.data.producers.BankPersUnit;
import com.abank.data.domain.Customer;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

/**
 * Created by: gruppd, 09.02.13 15:55
 */
public class CustomerDao implements CustomerDaoIf, Serializable {

    @Inject
    @BankPersUnit
    private EntityManager em;

    @Override
    public Customer createNew() {
        return new Customer();
    }

    @Override
    public void persist(Customer customer) {
        em.persist(customer);
    }

    @Override
    public void merge(Customer customer) {
        em.merge(customer);
    }

    @Override
    public List<Customer> findAll() {
        List<Customer> retList = em.createNamedQuery(Customer.QUERYID_FIND_ALL, Customer.class).getResultList();
        return retList;
    }

    @Override
    public Customer findById(long id) {
        Customer customer = em.createNamedQuery(Customer.QUERYID_FIND_BY_ID, Customer.class).getSingleResult();
        return customer;
    }
    
    @Override
    public Customer findByFLB(String firstname, String lastname, Calendar birthdate) {
    	Customer customer = null;
    	TypedQuery<Customer> query = em.createNamedQuery(Customer.QUERYID_FIND_BY_FLB, Customer.class);
    	query.setParameter("firstname", firstname);
    	query.setParameter("lastname", lastname);
    	query.setParameter("birthdate", birthdate);
    	customer = query.getSingleResult();
        return customer;
    }

    @Override
    public void delete(Customer customer) {
        em.remove(customer);
    }
}
