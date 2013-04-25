package com.abank.data.dao.impl;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.abank.data.dao.InfoMaterialOrderDaoIf;
import com.abank.data.domain.InfoMaterialOrder;
import com.abank.data.producers.BankPersUnit;

/**
 * Created by: gruppd, 09.02.13 15:17
 */
public class InfoMaterialOrderDao implements InfoMaterialOrderDaoIf, Serializable {

    private static final long serialVersionUID = 2615514780405208613L;

    @Inject
    @BankPersUnit
    private EntityManager em;

    @Override
    public InfoMaterialOrder createNew() {
        return new InfoMaterialOrder();
    }

    @Override
    public void persist(InfoMaterialOrder infoMaterialOrder) {
        em.persist(infoMaterialOrder);
    }

    @Override
    public void merge(InfoMaterialOrder infoMaterialOrder) {
        em.merge(infoMaterialOrder);
    }

    @Override
    public List<InfoMaterialOrder> findAll() {
        List<InfoMaterialOrder> retList = em.createNamedQuery(InfoMaterialOrder.QUERY_FIND_ALL, InfoMaterialOrder.class).getResultList();
        return retList;
    }

    @Override
    public InfoMaterialOrder findById(long id) {
    	InfoMaterialOrder infoMaterialOrder = em.createNamedQuery(InfoMaterialOrder.QUERY_FIND_BY_ID, InfoMaterialOrder.class).getSingleResult();
        return infoMaterialOrder;
    }

    @Override
    public void delete(InfoMaterialOrder infoMaterialOrder) {
        em.remove(infoMaterialOrder);
    }
}
