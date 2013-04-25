package com.abank.data.dao.impl;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.abank.data.dao.SystemuserDaoIf;
import com.abank.data.domain.Systemuser;
import com.abank.data.producers.BankPersUnit;

/**
 * Created by: gruppd, 09.02.13 15:55
 */
public class SystemuserDao implements SystemuserDaoIf, Serializable {

	private static final long serialVersionUID = 5905370315801537962L;
	
	@Inject
    @BankPersUnit
    private EntityManager em;

    @Override
    public Systemuser createNew() {
        return new Systemuser();
    }

    @Override
    public void persist(Systemuser systemUser) {
        em.persist(systemUser);
    }

    @Override
    public void merge(Systemuser systemUser) {
        em.merge(systemUser);
    }

    @Override
    public List<Systemuser> findAll() {
        List<Systemuser> retList = em.createNamedQuery(Systemuser.QUERY_FIND_ALL, Systemuser.class).getResultList();
        return retList;
    }

    @Override
    public Systemuser findById(long id) {
    	Systemuser systemUser = em.createNamedQuery(Systemuser.QUERY_FIND_BY_ID, Systemuser.class).getSingleResult();
        return systemUser;
    }
    
    @Override
    public List<Systemuser> findLoginPwd(String login, String pwd) {
    	List<Systemuser> systemUserList = null;
    	TypedQuery<Systemuser> query = em.createNamedQuery(Systemuser.QUERYID_FIND_LOGIN, Systemuser.class);
    	query.setParameter("login", login);
    	query.setParameter("password", pwd);
    	systemUserList = query.getResultList();
    	
        return systemUserList;
    }

    @Override
    public void delete(Systemuser systemUser) {
        em.remove(systemUser);
    }

}
