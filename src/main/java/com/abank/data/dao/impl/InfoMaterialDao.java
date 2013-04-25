package com.abank.data.dao.impl;

import com.abank.data.dao.InfoMaterialDaoIf;
import com.abank.data.domain.InfoMaterial;
import com.abank.data.producers.BankPersUnit;
import com.abank.jsf.InfoMaterialOrderTableEntry;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;

/**
 * Created by: gruppd, 09.02.13 15:17
 */
public class InfoMaterialDao implements InfoMaterialDaoIf, Serializable {

    private static final long serialVersionUID = 2615514780405208613L;

    @Inject
    @BankPersUnit
    private EntityManager em;

    @Override
    public InfoMaterial createNew() {
        return new InfoMaterial();
    }

    @Override
    public void persist(InfoMaterial infoMaterial) {
        em.persist(infoMaterial);
    }

    @Override
    public void merge(InfoMaterial infoMaterial) {
        em.merge(infoMaterial);
    }

    @Override
    public List<InfoMaterial> findAll() {
        List<InfoMaterial> retList = em.createNamedQuery(InfoMaterial.QUERY_FIND_ALL, InfoMaterial.class).getResultList();
        return retList;
    }

    @Override
    public List<InfoMaterialOrderTableEntry> findAllTableEntry() {
        List<InfoMaterialOrderTableEntry> retList = em.createNamedQuery(InfoMaterial.QUERYID_FIND_ALL_NEW, InfoMaterialOrderTableEntry.class).getResultList();
        return retList;
    }

    @Override
    public InfoMaterial findById(long id) {
        InfoMaterial infoMaterial = em.createNamedQuery(InfoMaterial.QUERY_FIND_BY_ID, InfoMaterial.class).getSingleResult();
        return infoMaterial;
    }

    @Override
    public void delete(InfoMaterial infoMaterial) {
        em.remove(infoMaterial);
    }
}
