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
import java.util.List;

/**
 * Arquillian test class to simply play around with the criteria API
 * The FROM clause (ProJPA2 page 251)
 * Created by: gruppd, 16.03.13 10:29
 */
@RunWith(Arquillian.class)
public class CriteriaApiTheFromClauseTest {

    final static Logger logger = Logger.getLogger(CriteriaApiTheFromClauseTest.class);

    CriteriaBuilder cb = null;

    @Deployment
    public static WebArchive createDeployment() {
        return DeploymentFactory.createDeployment();
    }

    @PersistenceContext(unitName = "JpaPU")
    EntityManager em;


    @Test
    @Ignore
    public void selectExplicitInnerJoin() {
        cb = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> cq = cb.createTupleQuery();
        Root<Customer> customerRoot = cq.from(Customer.class);
        Join<Customer, Phone> phoneJoin = customerRoot.join("phoneNr");
        cq.multiselect(customerRoot.get("firstname"), customerRoot.get("lastname"), phoneJoin.get("type"), phoneJoin.get("number"));
        TypedQuery<Tuple> query = em.createQuery(cq);
        System.out.println("\n\n");
        for (Tuple t : query.getResultList()) {
            System.out.println(t.get(0) + " " + t.get(1) + " (" + t.get(2) + ": " + t.get(3) + ")");
        }
        System.out.println("\n\n");
    }

    @Test
    @Ignore
    public void selectImplicitInnerJoin() {
        cb = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> cq = cb.createTupleQuery();
        Root<Customer> customerRoot = cq.from(Customer.class);
        cq.multiselect(customerRoot.get("firstname"), customerRoot.get("lastname"), customerRoot.get("phoneNr").get("type"), customerRoot.get("phoneNr").get("number"));
        TypedQuery<Tuple> query = em.createQuery(cq);
        System.out.println("\n\n");
        for (Tuple t : query.getResultList()) {
            System.out.println(t.get(0) + " " + t.get(1) + " (" + t.get(2) + ": " + t.get(3) + ")");
        }
        System.out.println("\n\n");
    }

    @Test
    @Ignore
    public void selectOuterJoin() {
        cb = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> cq = cb.createTupleQuery();
        Root<Employee> employeeRoot = cq.from(Employee.class);
        Join<Employee, Department> departmentJoin = employeeRoot.join("department", JoinType.LEFT);
        cq.multiselect(employeeRoot.get("id"), employeeRoot.get("firstname"), employeeRoot.get("lastname"), departmentJoin.get("id"), departmentJoin.get("departmentName"));
        cq.orderBy(cb.asc(employeeRoot.get("id")));
        TypedQuery<Tuple> query = em.createQuery(cq);
        System.out.println("\n\n");
        for (Tuple t : query.getResultList()) {
            System.out.println("Employee: " + t.get(0) + " " + t.get(1) + " " + t.get(2) + ", Department: " + t.get(3) + " " + t.get(4));
        }
        System.out.println("\n\n");
    }

    /**
     * The outer join is not applied since in the multiselect a navigation from employee to department seems to default to an inner join
     */
    @Test
    @Ignore
    public void selectOuterJoinButDefaultToInnerJoin() {
        cb = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> cq = cb.createTupleQuery();
        Root<Employee> employeeRoot = cq.from(Employee.class);
        Join<Employee, Department> departmentJoin = employeeRoot.join("department", JoinType.LEFT);
        cq.multiselect(employeeRoot.get("id"), employeeRoot.get("firstname"), employeeRoot.get("lastname"), employeeRoot.get("department").get("id"), departmentJoin.get("id"), departmentJoin.get("departmentName"));
        cq.orderBy(cb.asc(employeeRoot.get("id")));
        TypedQuery<Tuple> query = em.createQuery(cq);
        System.out.println("\n\n");
        for (Tuple t : query.getResultList()) {
            System.out.println("Employee: " + t.get(0) + " " + t.get(1) + " " + t.get(2) + " " + t.get(3) + ", Department: " + t.get(4) + " " + t.get(5));
        }
        System.out.println("\n\n");
    }



}
