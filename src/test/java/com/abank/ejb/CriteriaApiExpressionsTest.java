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
 * Part2: The WHERE clause - expressions (ProJPA2 page 254)
 * Created by: gruppd, 16.03.13 14:29
 */
@RunWith(Arquillian.class)
public class CriteriaApiExpressionsTest {

    final static Logger logger = Logger.getLogger(CriteriaApiExpressionsTest.class);

    CriteriaBuilder cb = null;

    @Deployment
    public static WebArchive createDeployment() {
        return DeploymentFactory.createDeployment();
    }

    @PersistenceContext(unitName = "JpaPU")
    EntityManager em;

    /**
     * Should be working but does not
     */
    @Test
    @Ignore
    public void selectSubQueryDoesNotWorkButWhy() {
        cb = em.getCriteriaBuilder();
        CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);
        Root<Employee> emp = cq.from(Employee.class);

        Subquery<Employee> sq = cq.subquery(Employee.class);
        Root<Department> dep = sq.from(Department.class);
        Join<Department, Employee> empDep = dep.join("employees");
        sq.select(empDep).where(cb.equal(dep.get("id"), 4));

        cq.select(emp).where(cb.in(emp).value(sq));

        TypedQuery<Employee> query = em.createQuery(cq);
        System.out.println("\n\n");
        for (Employee employee : query.getResultList()) {
            System.out.println(employee.toString());
        }
        System.out.println("\n\n");
    }

    /**
     * By querying the id directly the query with sub-query works
     */
    @Test
    @Ignore
    public void selectSubQueryIn() {
        cb = em.getCriteriaBuilder();
        CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);
        Root<Employee> emp = cq.from(Employee.class);

        Subquery<Long> sq = cq.subquery(Long.class);
        Root<Department> dep = sq.from(Department.class);
        Join<Department, Employee> empDep = dep.join("employees");
        sq.select(empDep.<Long>get("id")).where(cb.equal(dep.get("id"), 4));

        cq.select(emp).where(cb.in(emp.get("id")).value(sq));

        TypedQuery<Employee> query = em.createQuery(cq);
        System.out.println("\n\n");
        for (Employee employee : query.getResultList()) {
            System.out.println(employee.toString());
        }
        System.out.println("\n\n");
    }

    /**
     * same as selectSubQueryIn but using correlated query and EXIST instead of IN
     * SELECT emp
     * FROM   employee emp
     * WHERE  EXISTS (SELECT dep
     *               FROM   department dep JOIN dep.employees empDep
     *               WHERE  empDep.id = emp.id
     *               AND    dep.id = 4)
     */
    @Test
    @Ignore
    public void selectSubQueryExist() {
        cb = em.getCriteriaBuilder();
        CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);
        Root<Employee> emp = cq.from(Employee.class);

        Subquery<Employee> sq = cq.subquery(Employee.class);
        Root<Department> dep = sq.from(Department.class);
        Join<Department, Employee> empDep = dep.join("employees");
        sq.select(empDep).where(cb.equal(empDep.<Long>get("id"), emp.<Long>get("id")), cb.equal(dep.get("id"), 4));

        cq.select(emp).where(cb.exists(sq));

        TypedQuery<Employee> query = em.createQuery(cq);
        System.out.println("\n\n");
        for (Employee employee : query.getResultList()) {
            System.out.println(employee.toString());
        }
        System.out.println("\n\n");
    }

    /**
     * referencing root from the parent query in the FROM clause of a sub-query
     *
     * SELECT e
     * FROM   employee e
     * WHERE  EXISTS (SELECT dep
     *               FROM   e.department d
     *               WHERE  d.id = 4)
     */
    @Test
    @Ignore
    public void selectSubQueryExistFurther() {
        cb = em.getCriteriaBuilder();
        CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);
        Root<Employee> emp = cq.from(Employee.class);

        Subquery<Department> sq = cq.subquery(Department.class);
        Root<Employee> dep = sq.correlate(emp);
        Join<Employee, Department> department = dep.join("department");
        sq.select(department).where(cb.equal(department.get("id"), 4));

        cq.select(emp).where(cb.exists(sq));

        TypedQuery<Employee> query = em.createQuery(cq);
        System.out.println("\n\n");
        for (Employee employee : query.getResultList()) {
            System.out.println(employee.toString());
        }
        System.out.println("\n\n");
    }

    /**
     * Now referencing a join(!) expression from the parent query
     * in the FROM clause of a sub-query.
     *
     * List all branches with a manager whose direct reports earn more than a given threshold
     *
     * SELECT b
     * FROM   branch b JOIN b.employees e
     * WHERE  e.directs IS NOT EMPTY
     * AND    (SELECT AVG(e.salary) FROM e.directs) >= 135000
     *
     * ANSI-SQL:
     * SELECT b.*
     * FROM   branch b JOIN employee e ON e.branch_id = b.id
     * WHERE  e.emp_id in (SELECT a.manager_emp_id
     *                     FROM   employee a
     *                     WHERE  a.manager_emp_id IS NOT NULL  // direct reports
     *                     GROUP BY a.manager_emp_id
     *                     HAVING avg(a.salary) > 135000)
     */
    @Test
    @Ignore
    public void selectSubQueryExists() {
        cb = em.getCriteriaBuilder();
        CriteriaQuery<Branch> cq = cb.createQuery(Branch.class);
        Root<Branch> b = cq.from(Branch.class);
        Join<Branch, Employee> e = b.join("employees");

        Subquery<Double> sq = cq.subquery(Double.class);
        // FIRST "get the outer employee into the sub-query"
        Join<Branch,Employee> sqEmp = sq.correlate(e);
        // THEN make the join
        Join<Employee,Employee> directs = sqEmp.join("directs");
        sq.select(cb.avg(directs.<Double>get("salary")));

        cq.select(b).where(cb.isNotEmpty(e.<List>get("directs")), cb.ge(sq, 135000));

        TypedQuery<Branch> query = em.createQuery(cq);
        System.out.println("\n\n");
        for (Branch branch : query.getResultList()) {
            System.out.println(branch.toString());
        }
        System.out.println("\n\n");
    }

    /**
     * Usage of IN
     */
    @Test
    @Ignore
    public void selectQueryIn() {
        cb = em.getCriteriaBuilder();
        CriteriaQuery<Customer> cq = cb.createQuery(Customer.class);
        Root<Customer> c = cq.from(Customer.class);
        cq.select(c).where(cb.in(c.get("address").get("town")).value("Muenchen"));
        TypedQuery<Customer> query = em.createQuery(cq);
        System.out.println("\n\n");
        for (Customer customer : query.getResultList()) {
            System.out.println(customer.toString());
        }
        System.out.println("\n\n");
    }

    /**
     * Usage of IN second form
     */
    @Test
    @Ignore
    public void selectQueryInSecondForm() {
        cb = em.getCriteriaBuilder();
        CriteriaQuery<Customer> cq = cb.createQuery(Customer.class);
        Root<Customer> c = cq.from(Customer.class);
        cq.select(c).where(c.get("address").get("town").in("Muenchen", "Rostock"));
        TypedQuery<Customer> query = em.createQuery(cq);
        System.out.println("\n\n");
        for (Customer customer : query.getResultList()) {
            System.out.println(customer.toString());
        }
        System.out.println("\n\n");
    }

    @Test
    @Ignore
    public void selectQueryInList() {
        List<String> townList = new ArrayList<String>();
        townList.add("Muenchen");
        townList.add("Rostock");

        cb = em.getCriteriaBuilder();
        CriteriaQuery<Customer> cq = cb.createQuery(Customer.class);
        Root<Customer> c = cq.from(Customer.class);
        cq.select(c).where(c.get("address").get("town").in(townList)).orderBy(cb.asc(c.get("id")));
        TypedQuery<Customer> query = em.createQuery(cq);
        System.out.println("\n\n");
        for (Customer customer : query.getResultList()) {
            System.out.println(customer.toString());
        }
        System.out.println("\n\n");
    }

    @Test
    @Ignore
    public void selectQueryCaseTuple() {
        cb = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> cq = cb.createTupleQuery();
        Root<Customer> c = cq.from(Customer.class);
        cq.multiselect(c.get("firstname"), c.get("address").get("town"), cb.selectCase().when(cb.equal(c.get("address").get("town"),"Muenchen"),"Bayern").otherwise("Nowhere"));
        TypedQuery<Tuple> query = em.createQuery(cq);
        System.out.println("\n\n");
        for (Tuple t : query.getResultList()) {
            System.out.println("Firstname: " + t.get(0) + ",    Town: " + t.get(1) + ",    Region: " + t.get(2));
        }
        System.out.println("\n\n");
    }

    @Test
    @Ignore
    public void selectQueryCaseObject() {
        cb = em.getCriteriaBuilder();
        CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
        Root<Customer> c = cq.from(Customer.class);
        cq.multiselect(c.get("firstname"), c.get("address").get("town"), cb.selectCase().when(cb.equal(c.get("address").get("town"),"Muenchen"),"Bayern").otherwise("Nowhere"));
        TypedQuery<Object[]> query = em.createQuery(cq);
        List<Object[]> objects = query.getResultList();
        System.out.println("\n\n");
        for (Object[] object : objects) {
            System.out.println("Firstname: " + object[0] + ",   Town: " + object[1] + ",   Region: " + object[2]);
        }
        System.out.println("\n\n");
    }

    /**
     * Does not work but why?
     */
    @Test
    @Ignore
    public void selectQueryCaseObjectSimple() {
        cb = em.getCriteriaBuilder();
        CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
        Root<Customer> c = cq.from(Customer.class);
        //cq.multiselect(c.get("firstname"), c.get("address").get("town"), cb.selectCase(c.get("address").<String>get("town")).when("Muenchen","Bayern").otherwise("unkown"));
        cq.multiselect(c.get("firstname"), c.get("address").get("town"), cb.selectCase(c.get("firstname")).when("Gisela","Bayern").otherwise("unkown"));
        TypedQuery<Object[]> query = em.createQuery(cq);
        List<Object[]> objects = query.getResultList();
        System.out.println("\n\n");
        for (Object[] object : objects) {
            System.out.println("Firstname: " + object[0] + ",   Town: " + object[1] + ",   Region: " + object[2]);
        }
        System.out.println("\n\n");
    }

    /**
     * when turnover = null, then take salary
     * => nonsense, but technically ok
     */
    @Test
    @Ignore
    public void selectQueryCaseObjectCoalesceQueryWithNoSense() {
        cb = em.getCriteriaBuilder();
        CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
        Root<Employee> c = cq.from(Employee.class);
        cq.multiselect(c.get("firstname"), cb.coalesce(c.get("turnover"),c.get("salary")));
        TypedQuery<Object[]> query = em.createQuery(cq);
        List<Object[]> objects = query.getResultList();
        System.out.println("\n\n");
        for (Object[] object : objects) {
            System.out.println("Firstname: " + object[0] + ",   Salary: " + object[1]);
        }
        System.out.println("\n\n");
    }

    /**
     * Does not work but why? The next one which is querying on the branchId is working, though
     *
     * select all branches where the average salary is higher than a given threshold
     */
    @Test
    @Ignore
    public void selectQueryHavingDoesNotWork() {
        cb = em.getCriteriaBuilder();
        CriteriaQuery<Branch> cq = cb.createQuery(Branch.class);
        Root<Employee> e = cq.from(Employee.class);
        cq.select(e.<Branch>get("branch")).groupBy(e.<Branch>get("branch")).having(cb.ge(cb.avg(e.<Number>get("salary")),120000));
        TypedQuery<Branch> query = em.createQuery(cq);
        System.out.println("\n\n");
        for (Branch branch : query.getResultList()) {
            System.out.println(branch.toString());
        }
        System.out.println("\n\n");
    }

    /**
     * select all branches where the average salary is higher than a given threshold
     *
     * select b.id
     * FROM   employee e JOIN branch b ON b.id = e.branchId
     * GROUP BY e.branchId
     * HAVING AVG(e.salary) > 100000
     */
    @Test
    public void selectQueryHaving() {
        cb = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> cq = cb.createQuery(Tuple.class);
        Root<Employee> e = cq.from(Employee.class);
        //cq.select(e.<Branch>get("branch")).groupBy(e.<Branch>get("branch")).having(cb.ge(cb.avg(e.<Number>get("salary")),120000));
        cq.multiselect(e.<Branch>get("branch").<String>get("id")).groupBy(e.<Branch>get("branch")).having(cb.ge(cb.avg(e.<Number>get("salary")),120000));
        TypedQuery<Tuple> query = em.createQuery(cq);
        System.out.println("\n\n");
        for (Tuple t : query.getResultList()) {
            System.out.println(t.get(0));
        }
        System.out.println("\n\n");
    }


}
