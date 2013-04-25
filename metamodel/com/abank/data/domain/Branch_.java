package com.abank.data.domain;

import com.abank.data.domain.Account;
import com.abank.data.domain.Address;
import com.abank.data.domain.Department;
import com.abank.data.domain.Employee;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2013-03-25T20:36:36")
@StaticMetamodel(Branch.class)
public class Branch_ { 

    public static volatile SingularAttribute<Branch, Long> id;
    public static volatile ListAttribute<Branch, Account> accounts;
    public static volatile SingularAttribute<Branch, Address> address;
    public static volatile SingularAttribute<Branch, String> branchName;
    public static volatile ListAttribute<Branch, Department> departments;
    public static volatile SetAttribute<Branch, Employee> employees;
    public static volatile SingularAttribute<Branch, Integer> version;

}