package com.abank.data.domain;

import com.abank.data.domain.Account;
import com.abank.data.domain.Address;
import com.abank.data.domain.Phone;
import java.util.Calendar;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2013-03-25T20:36:36")
@StaticMetamodel(Customer.class)
public class Customer_ { 

    public static volatile SingularAttribute<Customer, Long> id;
    public static volatile ListAttribute<Customer, Account> accounts;
    public static volatile SingularAttribute<Customer, Address> address;
    public static volatile SingularAttribute<Customer, String> lastname;
    public static volatile SingularAttribute<Customer, Calendar> birthDate;
    public static volatile SingularAttribute<Customer, String> firstname;
    public static volatile ListAttribute<Customer, Phone> phoneNr;

}