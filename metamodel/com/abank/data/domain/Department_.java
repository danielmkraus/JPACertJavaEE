package com.abank.data.domain;

import com.abank.data.domain.Employee;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2013-03-25T20:36:36")
@StaticMetamodel(Department.class)
public class Department_ { 

    public static volatile SingularAttribute<Department, Long> id;
    public static volatile SingularAttribute<Department, String> departmentName;
    public static volatile SetAttribute<Department, Employee> employees;
    public static volatile SingularAttribute<Department, Integer> version;

}