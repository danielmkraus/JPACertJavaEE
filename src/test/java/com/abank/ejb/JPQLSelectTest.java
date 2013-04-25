package com.abank.ejb;

import com.abank.data.domain.Customer;
import org.apache.log4j.Logger;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Arquillian test class to simply play around with the criteria API
 * Part2: The WHERE clause - expressions (ProJPA2 page 254)
 * Created by: gruppd, 16.03.13 14:29
 */
@RunWith(Arquillian.class)
public class JPQLSelectTest {

    final static Logger logger = Logger.getLogger(JPQLSelectTest.class);

    final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-mm-dd");

    @Deployment
    public static WebArchive createDeployment() {
        return DeploymentFactory.createDeployment();
    }

    @PersistenceContext(unitName = "JpaPU")
    EntityManager em;

    @Test
    @Ignore
    public void selectSimple() {
        final Query query = em.createQuery("SELECT c.firstname, c.lastname, c.birthDate " +
                "FROM Customer c " +
                "WHERE c.birthDate > :birthDate " +
                "ORDER BY c.birthDate ASC", Customer.class);
        query.setParameter("birthDate", getCalendar("1990-01-01"));
        List<Object> result = query.getResultList();
        printOut(result);
    }

    @Test
    @Ignore
    public void selectSimpleWhereOne() {
        final Query query = em.createQuery("SELECT DISTINCT (e.firstname) " +
                "FROM Employee e " +
                "ORDER BY e.firstname ");
        List<Object> result = query.getResultList();
        printOut(result);
    }

    @Test
    @Ignore
    public void selectNew() {
        final Query query = em.createQuery("SELECT NEW com.abank.ejb.CustomerInfo(c.firstname, c.lastname) " +
                "FROM Customer c ");
        List<Object> result = query.getResultList();
        printOut(result);
    }

    @Test
    @Ignore
    public void selectIsNotEmpty() {
        final Query query = em.createQuery("SELECT d " +
                "FROM Department d " +
                "WHERE d.employees IS NOT EMPTY ");
        List<Object> result = query.getResultList();
        printOut(result);
    }

    @Test
    @Ignore
    public void selectType() {
        final Query query = em.createQuery("SELECT p " +
                "FROM Project p " +
                "WHERE TYPE(p) = DesignProject ");
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
                if (object instanceof Calendar) {
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
