package com.abank.ejb;

import com.abank.data.dao.CustomerDaoIf;
import com.abank.data.domain.Customer;
import com.abank.data.producers.BankPersUnit;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;

/**
 * Created by: gruppd, 12.03.13 18:48
 */
@Stateless
public class CriteriaSelector implements Serializable {

    final static Logger logger = Logger.getLogger(CriteriaSelector.class);

    @Inject
    @BankPersUnit
    private EntityManager em;

    public void executeMyQuery() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Customer> c = cb.createQuery(Customer.class);
        Root<Customer> customerRoot = c.from(Customer.class);
        c.select(customerRoot).where(cb.equal(customerRoot.get("firstname"), "Hella"));

        TypedQuery<Customer> q = em.createQuery(c);
        List<Customer> customerList = q.getResultList();
        logger.info("The following customers have been found");
        for (Customer customer : customerList) {
            logger.info(customer);
        }
    }


}
