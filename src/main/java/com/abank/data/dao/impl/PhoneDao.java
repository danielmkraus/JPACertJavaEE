package com.abank.data.dao.impl;

import com.abank.data.dao.PhoneDaoIf;
import com.abank.data.domain.Phone;
import com.abank.data.producers.BankPersUnit;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by: gruppd, 09.02.13 15:55
 */
public class PhoneDao implements PhoneDaoIf {

    @Inject
    @BankPersUnit
    private EntityManager em;

    @Override
    public Phone createNew() {
        return new Phone();
    }

    @Override
    public void persist(Phone phone) {
        em.persist(phone);
    }

    @Override
    public void merge(Phone phone) {
        em.merge(phone);
    }

    @Override
    public List<Phone> findAll() {
        List<Phone> retList = em.createNamedQuery(Phone.QUERY_FIND_ALL, Phone.class).getResultList();
        return retList;
    }

    @Override
    public Phone findById(long id) {
        Phone phone = em.createNamedQuery(Phone.QUERY_FIND_BY_ID, Phone.class).getSingleResult();
        return phone;
    }

    @Override
    public void delete(Phone customer) {
        em.remove(customer);
    }
}
