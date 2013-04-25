package com.abank.ejb;

import com.abank.data.domain.*;
import org.apache.log4j.Logger;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Arquillian test class to simply play around with the criteria API
 * Part1: The WHERE clause (ProJPA2 page 254)
 * Created by: gruppd, 16.03.13 14:29
 */
@RunWith(Arquillian.class)
public class CriteriaApiTheWhereClauseTest {

    final static Logger logger = Logger.getLogger(CriteriaApiTheWhereClauseTest.class);

    CriteriaBuilder cb = null;

    @Deployment
    public static WebArchive createDeployment() {
        return DeploymentFactory.createDeployment();
    }

    @PersistenceContext(unitName = "JpaPU")
    EntityManager em;

    @Test
    @Ignore
    public void selectOptions() {
        cb = em.getCriteriaBuilder();
        CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);
        Root<Employee> emp = cq.from(Employee.class);
        cq.select(emp).where(cb.isNull(emp.get("department")));
        TypedQuery<Employee> query = em.createQuery(cq);
        System.out.println("\n\n");
        for (Employee e : query.getResultList()) {
            System.out.println(e.toString());
        }
        System.out.println("\n\n");
    }

    @Test
    @Ignore
    public void selectMoreOptions() {
        cb = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> cq = cb.createTupleQuery();
        Root<Customer> customerRoot = cq.from(Customer.class);
        Join<Customer, Phone> phoneJoin = customerRoot.join("phoneNr");
        cq.multiselect(cb.currentDate(), cb.upper(customerRoot.<String>get("firstname")), cb.lower(customerRoot.<String>get("lastname")), cb.concat((cb.concat(phoneJoin.<String>get("type"), "-")), phoneJoin.<String>get("number")), cb.length(phoneJoin.<String>get("number")));

        Predicate criteria = cb.conjunction();
        criteria = cb.and(criteria, cb.equal(phoneJoin.<String>get("type"), "fest"));
        criteria = cb.and(criteria, cb.equal(cb.length(phoneJoin.<String>get("number")), 9));
        criteria = cb.and(criteria, cb.like(customerRoot.<String>get("firstname"), "A%"));
        cq.where(criteria);

        TypedQuery<Tuple> query = em.createQuery(cq);
        System.out.println("\n\n");
        for (Tuple t : query.getResultList()) {
            System.out.println(t.get(0) + " " + t.get(1) + " " + t.get(2) + " (" + t.get(3) + ": " + t.get(4) + ")");
        }
        System.out.println("\n\n");
    }


    @Test
    @Ignore
    public void selectMoreOptionsWithParameters() {
        cb = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> cq = cb.createTupleQuery();
        Root<Customer> customerRoot = cq.from(Customer.class);
        Join<Customer, Phone> phoneJoin = customerRoot.join("phoneNr");
        cq.multiselect(cb.upper(customerRoot.<String>get("firstname")), cb.lower(customerRoot.<String>get("lastname")), cb.concat((cb.concat(phoneJoin.<String>get("type"), "-")), phoneJoin.<String>get("number")), cb.length(phoneJoin.<String>get("number")));

        // Parameters, get them into the method via method parameters e.g. via a servlet
        String phoneType = "fest";
        Integer lengthPhoneNumber = 9;
        String firstNameLike = "A%";

        Predicate criteria = cb.conjunction();
        if (phoneType != null) {
            ParameterExpression<String> p = cb.parameter(String.class, "phoneTypeParam");
            criteria = cb.and(criteria, cb.equal(phoneJoin.<String>get("type"), p));
        }
        if (lengthPhoneNumber != null) {
            ParameterExpression<Integer> p = cb.parameter(Integer.class, "lengthPhoneNumberParam");
            criteria = cb.and(criteria, cb.equal(cb.length(phoneJoin.<String>get("number")), lengthPhoneNumber));
        }
        if (firstNameLike != null) {
            ParameterExpression<String> p = cb.parameter(String.class, "firstNameLikeParam");
            criteria = cb.and(criteria, cb.like(customerRoot.<String>get("firstname"), p));
        }
        cq.where(criteria);

        TypedQuery<Tuple> query = em.createQuery(cq);
        if (phoneType != null) {
            query.setParameter("phoneTypeParam", phoneType);
        }
        if (lengthPhoneNumber != null) {
            query.setParameter("lengthPhoneNumberParam", lengthPhoneNumber);
        }
        if (firstNameLike != null) {
            query.setParameter("firstNameLikeParam", firstNameLike);
        }

        System.out.println("\n\n");
        for (Tuple t : query.getResultList()) {
            System.out.println(t.get(0) + " " + t.get(1) + " (" + t.get(2) + ": " + t.get(3) + ")");
        }
        System.out.println("\n\n");
    }


}
