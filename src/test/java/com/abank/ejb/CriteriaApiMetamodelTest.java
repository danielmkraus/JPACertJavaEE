package com.abank.ejb;

import com.abank.data.domain.*;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.Calendar;
import java.util.List;

/**
 * Arquillian test class to simply play around with the criteria API
 * The Metamodel API (ProJPA2 page 265)
 * Created by: gruppd, 12.03.13 19:29
 */
@RunWith(Arquillian.class)
public class CriteriaApiMetamodelTest {

    CriteriaBuilder cb = null;

    @Deployment
    public static WebArchive createDeployment() {
        return DeploymentFactory.createDeployment();
    }

    @PersistenceContext(unitName = "JpaPU")
    EntityManager em;

    @Test
    @Ignore
    public void selectEntity() {
        final Calendar dateLimit = Calendar.getInstance();
        dateLimit.set(Calendar.YEAR, 2010);
        dateLimit.set(Calendar.MONTH, Calendar.JANUARY);
        dateLimit.set(Calendar.DAY_OF_MONTH, 1);

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Customer> cq = cb.createQuery(Customer.class);
        Root<Customer> cust = cq.from(Customer.class);
        Join<Customer, Account> acc = cust.join(Customer_.accounts);
        cq.select(cust);

        Predicate criteria = cb.conjunction();
        criteria = cb.and(criteria, cb.isNull(acc.get(Account_.closeDate)));
        criteria = cb.and(criteria, cb.equal(acc.get(Account_.accountType), "Savings Account"));
        criteria = cb.and(criteria, cb.greaterThan(acc.get(Account_.openDate), dateLimit));
        cq.where(criteria);
        cq.orderBy(cb.asc(cust.get(Customer_.id)));

        TypedQuery<Customer> query = em.createQuery(cq);
        List<Customer> branchList = query.getResultList();
        System.out.println("\n\n");
        for (Customer branch : branchList) {
            System.out.println(branch.toString());
        }
        System.out.println("\n\n");
    }

    /**
     * count in the select implies no order by...
     */
    @Test
     public void selectEntityCount() {
        final Calendar dateLimit = Calendar.getInstance();
        dateLimit.set(Calendar.YEAR, 2010);
        dateLimit.set(Calendar.MONTH, Calendar.JANUARY);
        dateLimit.set(Calendar.DAY_OF_MONTH, 1);

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Customer> cust = cq.from(Customer.class);
        Join<Customer, Account> acc = cust.join(Customer_.accounts);
        cq.multiselect(cb.count(cust.get(Customer_.id)));

        Predicate criteria = cb.conjunction();
        criteria = cb.and(criteria, cb.isNull(acc.get(Account_.closeDate)));
        criteria = cb.and(criteria, cb.equal(acc.get(Account_.accountType), "Savings Account"));
        criteria = cb.and(criteria, cb.greaterThan(acc.get(Account_.openDate), dateLimit));
        cq.where(criteria);

        TypedQuery<Long> query = em.createQuery(cq);
        List<Long> objects = query.getResultList();
        System.out.println("\n\n");
        for (Long object : objects) {
            System.out.println("Count: " + object.toString());
        }
        System.out.println("\n\n");
    }

}
