package com.abank.data.domain;

import com.abank.data.domain.Branch;
import com.abank.data.domain.Customer;
import java.util.Calendar;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2013-03-25T20:36:36")
@StaticMetamodel(Account.class)
public class Account_ { 

    public static volatile SingularAttribute<Account, Long> bankIdentifierNumber;
    public static volatile SingularAttribute<Account, Long> id;
    public static volatile SingularAttribute<Account, Calendar> closeDate;
    public static volatile SingularAttribute<Account, String> accountType;
    public static volatile SingularAttribute<Account, Branch> branch;
    public static volatile SingularAttribute<Account, Long> bankAccountNumber;
    public static volatile SingularAttribute<Account, Customer> customer;
    public static volatile SingularAttribute<Account, Calendar> openDate;
    public static volatile SingularAttribute<Account, String> swift;
    public static volatile SingularAttribute<Account, String> iban;
    public static volatile SingularAttribute<Account, Integer> version;

}