package com.abank.ejb;

import com.abank.data.domain.Branch;
import com.abank.data.domain.Customer;
import com.sun.xml.ws.api.tx.at.Transactional;
import org.apache.log4j.Logger;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.PersistenceTest;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Arquillian test class to simply play around with the criteria API
 * Part1: The SELECT clause (ProJPA2 page 249)
 * Created by: gruppd, 12.03.13 19:29
 */
@RunWith(Arquillian.class)
public class CriteriaApiTheSelectClauseTest {

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
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Branch> cq = cb.createQuery(Branch.class);
        Root<Branch> branchRoot = cq.from(Branch.class);
        cq.select(branchRoot); //.where(cb.equal(branchRoot.get("branchName"), "black"));
        TypedQuery<Branch> query = em.createQuery(cq);
        List<Branch> branchList = query.getResultList();
        System.out.println("\n\n");
        for (Branch branch : branchList) {
            System.out.println(branch.toString());
        }
        System.out.println("\n\n");
    }

    @Test
    @Ignore
    public void selectSingleExpression() {
        cb = em.getCriteriaBuilder();
        CriteriaQuery<String> cq = cb.createQuery(String.class);
        Root<Customer> customerRoot = cq.from(Customer.class);
        cq.select(customerRoot.<String>get("firstname"));
        TypedQuery<String> query = em.createQuery(cq);
        List<String> customerList = query.getResultList();
        System.out.println("\n\n");
        for (String customer : customerList) {
            System.out.println(customer.toString());
        }
        System.out.println("\n\n");
    }

    @Test
    @Ignore
    public void selectMultipleExpressionsTupleSecond() {
        cb = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> cq = cb.createTupleQuery();
        Root<Customer> customerRoot = cq.from(Customer.class);
        cq.select(cb.tuple(customerRoot.get("firstname"), customerRoot.get("lastname")));
        TypedQuery<Tuple> query = em.createQuery(cq);
        System.out.println("\n\n");
        for (Tuple t : query.getResultList()) {
            System.out.println("Vorname: " + t.get(0) + ",    Nachname: " + t.get(1));
        }
        System.out.println("\n\n");
    }

    @Test
    @Ignore
    public void selectMultipleExpressionsMultiselectFirstForm() {
        cb = em.getCriteriaBuilder();
        CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
        Root<Customer> customerRoot = cq.from(Customer.class);
        cq.multiselect(customerRoot.get("firstname"), customerRoot.get("lastname"), customerRoot.get(""));
        TypedQuery<Object[]> query = em.createQuery(cq);
        List<Object[]> objects = query.getResultList();
        System.out.println("\n\n");
        for (Object[] object : objects) {
            System.out.println("Vorname: " + object[0] + ",    Nachname: " + object[1]);
        }
        System.out.println("\n\n");
    }

    @Test
    @Ignore
    public void selectMultipleExpressionsMultiselectSecondForm() {
        cb = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> cq = cb.createTupleQuery();
        Root<Customer> customerRoot = cq.from(Customer.class);
        cq.multiselect(customerRoot.get("firstname").alias("firstname"), customerRoot.get("lastname").alias("lastname"));
        TypedQuery<Tuple> query = em.createQuery(cq);
        System.out.println("\n\n");
        for (Tuple t : query.getResultList()) {
            System.out.println("Vorname: " + t.get("firstname", String.class) + ",    Nachname: " + t.get("lastname", String.class));
        }
        System.out.println("\n\n");
    }

    @Test
    @Ignore
    public void selectMultipleExpressionsMultiselectThirdForm() {
        cb = em.getCriteriaBuilder();
        CriteriaQuery<CustomerInfo> cq = cb.createQuery(CustomerInfo.class);
        Root<Customer> customerRoot = cq.from(Customer.class);
        cq.multiselect(customerRoot.get("firstname").alias("firstname"), customerRoot.get("lastname").alias("lastname"));
        TypedQuery<CustomerInfo> query = em.createQuery(cq);
        System.out.println("\n\n");
        for (CustomerInfo t : query.getResultList()) {
            System.out.println("Vorname: " + t.getFirstname() + ",    Nachname: " + t.getLastname());
        }
        System.out.println("\n\n");
    }

    @Test
    @Ignore
    public void selectMultipleExpressionsConstruct() {
        cb = em.getCriteriaBuilder();
        CriteriaQuery<CustomerInfo> cq = cb.createQuery(CustomerInfo.class);
        Root<Customer> customerRoot = cq.from(Customer.class);
        cq.select(cb.construct(CustomerInfo.class, customerRoot.get("firstname"), customerRoot.get("lastname")));
        TypedQuery<CustomerInfo> query = em.createQuery(cq);
        System.out.println("\n\n");
        for (CustomerInfo t : query.getResultList()) {
            System.out.println("Vorname: " + t.getFirstname() + ",    Nachname: " + t.getLastname());
        }
        System.out.println("\n\n");
    }

    @Test
    @Ignore
    public void selectMultipleExpressionsArray() {
        cb = em.getCriteriaBuilder();
        CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
        Root<Customer> customerRoot = cq.from(Customer.class);
        cq.select(cb.array(customerRoot.get("firstname"), customerRoot.get("lastname")));
        TypedQuery<Object[]> query = em.createQuery(cq);
        List<Object[]> objects = query.getResultList();
        System.out.println("\n\n");
        for (Object[] object : objects) {
            System.out.println("Vorname: " + object[0] + ",    Nachname: " + object[1]);
        }
        System.out.println("\n\n");
    }

}
