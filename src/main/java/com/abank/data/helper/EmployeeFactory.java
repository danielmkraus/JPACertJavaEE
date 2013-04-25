package com.abank.data.helper;

import com.abank.data.domain.Employee;
import com.abank.data.domain.Phone;

import java.util.*;
import java.util.logging.Logger;

/**
 * Created by: gruppd, 09.02.13 16:16
 */
public class EmployeeFactory {

    public static final Logger logger = Logger.getLogger(EmployeeFactory.class.getCanonicalName());

    private static Random generator = new Random();

    private EmployeeFactory() {
    }

    public static void main(String[] args) {
        List<Employee> lC = createEmployees(10);
        for (Employee employee : lC) {
            System.out.println(employee.toString());
        }
    }

    public static List<Employee> createEmployees(int number) {
        List<Employee> employeeList = new ArrayList<Employee>();
        for (int i = 0; i < number; i++) {
            employeeList.add(generateRandomEmployee());
        }
        return employeeList;
    }

    private static Employee generateRandomEmployee() {
        Employee emp = new Employee();
        emp.setFirstname(DataArray.firstNames[generator.nextInt(390)]);
        emp.setLastname(DataArray.lastNames[generator.nextInt(100)]);

        Calendar birthDate = DataCreatorHelper.generateRandomCalendar(1960, 1970);
        emp.setBirthDate(birthDate);
        Calendar startDate = DataCreatorHelper.generateRandomCalendar(birthDate.get(Calendar.YEAR) + 18, 2008);
        emp.setStartDate(startDate);
        Calendar endDate = DataCreatorHelper.generateEndDate(startDate);
        emp.setEndDate(endDate);

        String town = DataArray.towns[generator.nextInt(9)];
        List<Phone> phoneList = PhoneListFactory.getPhoneList(town);
        emp.setPhoneNr(phoneList);

        Long salary = new Long(DataCreatorHelper.randIntBetween(14000, 200000));
        emp.setSalary(salary);

        if (DataCreatorHelper.randIntBetween(0,4) == 2) {
            emp.setUnionMember(Boolean.TRUE);
        } else {
            emp.setUnionMember(Boolean.FALSE);
        }

        return emp;
    }

    public static Employee generateTestEmployee() {
    	
    	Employee testEmployee = new Employee();
        testEmployee.setFirstname("aaa");
        testEmployee.setLastname("aaa");
        testEmployee.setBirthDate(new GregorianCalendar(1970, Calendar.FEBRUARY, 8));
        testEmployee.setStartDate(new GregorianCalendar(1990, Calendar.FEBRUARY, 8));
        
        String town = DataArray.towns[generator.nextInt(9)];
        List<Phone> phoneList = PhoneListFactory.getPhoneList(town);
        testEmployee.setPhoneNr(phoneList);
        
        Long salary = new Long(DataCreatorHelper.randIntBetween(14000, 200000));
        testEmployee.setSalary(salary);
        
        return testEmployee;
    }

}
