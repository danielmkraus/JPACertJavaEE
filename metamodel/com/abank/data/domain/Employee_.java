package com.abank.data.domain;

import com.abank.data.domain.Branch;
import com.abank.data.domain.Department;
import com.abank.data.domain.Employee;
import com.abank.data.domain.Phone;
import com.abank.data.domain.Project;
import java.math.BigDecimal;
import java.util.Calendar;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2013-03-25T20:36:36")
@StaticMetamodel(Employee.class)
public class Employee_ { 

    public static volatile SingularAttribute<Employee, Calendar> startDate;
    public static volatile SingularAttribute<Employee, String> jobRole;
    public static volatile SingularAttribute<Employee, Employee> manager;
    public static volatile SingularAttribute<Employee, Department> department;
    public static volatile SingularAttribute<Employee, String> lastname;
    public static volatile SingularAttribute<Employee, Calendar> endDate;
    public static volatile SingularAttribute<Employee, String> firstname;
    public static volatile ListAttribute<Employee, Phone> phoneNr;
    public static volatile SingularAttribute<Employee, Integer> version;
    public static volatile SingularAttribute<Employee, Long> id;
    public static volatile ListAttribute<Employee, Project> projects;
    public static volatile SingularAttribute<Employee, BigDecimal> turnover;
    public static volatile ListAttribute<Employee, Employee> directs;
    public static volatile SingularAttribute<Employee, Branch> branch;
    public static volatile SingularAttribute<Employee, Calendar> birthDate;
    public static volatile SingularAttribute<Employee, Long> salary;
    public static volatile SingularAttribute<Employee, Boolean> unionMember;

}