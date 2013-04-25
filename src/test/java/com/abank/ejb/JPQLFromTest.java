package com.abank.ejb;

import com.abank.data.domain.Customer;
import com.abank.data.domain.Department;
import com.abank.data.domain.Employee;
import com.abank.data.domain.Project;
import com.abank.data.domain.enums.JobRoleEnum;
import org.apache.log4j.Logger;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Arquillian test class to simply play around with the criteria API
 * Part2: The WHERE clause - expressions (ProJPA2 page 254)
 * Created by: gruppd, 16.03.13 14:29
 */
@RunWith(Arquillian.class)
public class JPQLFromTest {

    final static Logger logger = Logger.getLogger(JPQLFromTest.class);

    final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-mm-dd");

    @Deployment
    public static WebArchive createDeployment() {
        return DeploymentFactory.createDeployment();
    }

    @PersistenceContext(unitName = "JpaPU")
    EntityManager em;

    @Test
    @Ignore
    public void whereUsingJoinTest() {
        final Query query = em.createQuery("SELECT c.firstname, c.lastname, c.birthDate, a.accountType, a.bankAccountNumber, a.bankIdentifierNumber " +
                "FROM  Customer c JOIN c.accounts a " +
                "WHERE a.branch.branchName = :branchName " +
                "AND   a.accountType = :accountType " +
                "ORDER BY c.lastname DESC", Customer.class);
        query.setParameter("branchName", "Hamburg");
        query.setParameter("accountType", "Giro Account");
        List<Object> result = query.getResultList();
        printOut(result);
    }

    @Test
    @Ignore
    public void whereImplicitJoinInWhereClauseTest() {
        final Query query = em.createQuery("SELECT c.firstname, c.lastname, c.birthDate, a.accountType, a.bankAccountNumber, a.bankIdentifierNumber " +
                "FROM  Customer c, Account a " +
                "WHERE c = a.customer " +
                "AND   a.branch.branchName = :branchName " +
                "AND   a.accountType = :accountType " +
                "ORDER BY c.lastname DESC", Customer.class);
        query.setParameter("branchName", "Hamburg");
        query.setParameter("accountType", "Giro Account");
        List<Object> result = query.getResultList();
        printOut(result);
    }

    @Test
    @Ignore
    public void whereOuterJoinTest() {
        final Query query = em.createQuery("SELECT e, d " +
                "FROM  Employee e LEFT JOIN e.department d " +
                "WHERE e.endDate IS NOT NULL " +
                "ORDER BY e.id ASC", Department.class);
        List<Object> result = query.getResultList();
        printOut(result);
    }

    /**
     * nicht sichtbar in der Ausgabe: die Employees haben auch eine gefüllte Department entity
     */
    @Test
    @Ignore
    public void whereFetchJoinTest() {
        final Query query = em.createQuery("SELECT e " +
                "FROM  Employee e LEFT JOIN FETCH e.department " +
                "WHERE e.endDate IS NOT NULL " +
                "ORDER BY e.id ASC", Department.class);
        List<Object> result = query.getResultList();
        printOut(result);
    }

    @Test
    @Ignore
    public void whereBooleanTest() {
        final Query query = em.createQuery("SELECT e.id, e.firstname, e.lastname, e.unionMember, d.departmentName " +
                "FROM  Employee e JOIN e.department d " +
                "WHERE e.unionMember = TRUE " +
                "AND   e.jobRole = :jobRole " +
                "ORDER BY e.id ASC", Department.class);
        query.setParameter("jobRole", JobRoleEnum.SYSTEM_ANALYST.getStrValue());
        List<Object> result = query.getResultList();
        printOut(result);
    }

    @Test
    @Ignore
    public void whereBetweenTest() {
        Project pSearch = new Project();
        pSearch.setId(1l);
        final Query query = em.createQuery("SELECT e.id, e.firstname, e.lastname, e.jobRole, e.salary, d.departmentName " +
                "FROM  Employee e JOIN e.department d " +
                "WHERE :project MEMBER OF e.projects " +
                "AND   ( (e.jobRole = :jobRoleOne) OR (e.jobRole = :jobRoleTwo)) " +
                "AND   e.salary BETWEEN 30000 AND 100000 " +
                "AND   e.directs IS NOT EMPTY " +
                "ORDER BY e.id ASC", Department.class);
        query.setParameter("jobRoleOne", JobRoleEnum.LAWYER.getStrValue());
        query.setParameter("jobRoleTwo", JobRoleEnum.TEAM_ASS.getStrValue());
        query.setParameter("project", pSearch);
        List<Object> result = query.getResultList();
        printOut(result);
    }

    @Test
    @Ignore
    public void whereSubquery() {
        final Query query = em.createQuery("SELECT e " +
                "FROM  Employee e " +
                "WHERE e.salary = (SELECT MAX(emp.salary) " +
                "                  FROM    Employee emp) ", Employee.class);
        List<Object> result = query.getResultList();
        printOut(result);
    }

    /**
     * semantically not really enlightening but technically ok
     */
    @Test
    @Ignore
    public void whereCorrelatedSubquery() {
        final Query query = em.createQuery("SELECT e " +
                "FROM  Employee e " +
                "WHERE EXISTS (SELECT 1 FROM e.department d " +
                "              WHERE  e MEMBER OF d.employees " +
                "              AND    d.employees IS NOT EMPTY) ", Employee.class);
        List<Object> result = query.getResultList();
        printOut(result);
    }

    @Test
    @Ignore
    public void whereQueryMin() {
        final Query query = em.createQuery("SELECT MIN(e.salary) " +
                "FROM  Employee e",  Employee.class);
        List<Object> result = query.getResultList();
        printOut(result);
    }

    @Test
    @Ignore
    public void whereSubqueryMin() {
        final Query query = em.createQuery("SELECT e " +
                "FROM  Employee e " +
                "WHERE e.salary = (SELECT  MIN(d.salary) " +
                "                  FROM    Employee d) ", Employee.class);
        List<Object> result = query.getResultList();
        printOut(result);
    }

    @Test
    @Ignore
    public void whereSubqueryAll() {
        final Query query = em.createQuery("SELECT e " +
                "FROM  Employee e " +
                "WHERE e.salary < ALL (SELECT  d.salary " +
                "                      FROM    Employee d " +
                "                      WHERE   e.id <> d.id) ", Employee.class);
        List<Object> result = query.getResultList();
        printOut(result);
    }

    @Test
    @Ignore
    public void whereTemporalLiteral() {
        final Query query = em.createQuery("SELECT c " +
                "FROM  Customer c " +
                "WHERE c.birthDate > {d '1990-01-01'} ");
        List<Object> result = query.getResultList();
        printOut(result);
    }

    @Test
    @Ignore
    public void whereType() {
        final Query query = em.createQuery("SELECT p " +
                "FROM  Project p " +
                "WHERE TYPE(p) = DesignProject ");
        List<Object> result = query.getResultList();
        printOut(result);
    }

    @Test
    public void whereCurrentDate() {
        final Query query = em.createQuery("SELECT COUNT(e), ' employees as of ', CURRENT_TIMESTAMP " +
                "FROM  Employee e ");
        List<Object> result = query.getResultList();
        printOut(result);
    }



    private void printOut(List<Object> objectList) {
        System.out.println("\n\n");
        if (objectList.isEmpty()) {
            System.out.println("No results found");
        } else {
            for (Object field : objectList) {
                System.out.println(resultAsString(field));
            }
        }
        System.out.println("\n\n");
    }

    private Calendar getCalendar(String datePattern) {
        Calendar calendarRetValue = Calendar.getInstance();
        try {
            Date date = SDF.parse(datePattern);
            calendarRetValue.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return calendarRetValue;
    }

    private String resultAsString(Object o) {
        if (o instanceof Object[]) {
            StringBuffer resultString = new StringBuffer();
            for (int i = 0; i < ((Object[]) o).length; i++) {
                resultString.append("[");
                Object[] objects = (Object[])o;
                Object object = objects[i];
                if (null == object) {
                    resultString.append("null");
                } else if (object instanceof Calendar) {
                    resultString.append(SDF.format(((Calendar) object).getTime()));
                } else {
                    resultString.append(object.toString());
                }
                resultString.append("] ");
            }

            return resultString.toString();
        } else {
            return String.valueOf(o);
        }
    }


}
