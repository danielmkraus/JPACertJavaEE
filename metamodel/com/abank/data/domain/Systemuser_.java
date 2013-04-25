package com.abank.data.domain;

import java.util.Calendar;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2013-03-25T20:36:36")
@StaticMetamodel(Systemuser.class)
public class Systemuser_ { 

    public static volatile SingularAttribute<Systemuser, Long> id;
    public static volatile SingularAttribute<Systemuser, Calendar> lastLoginDate;
    public static volatile SingularAttribute<Systemuser, Calendar> lastLogoutDate;
    public static volatile SingularAttribute<Systemuser, String> login;
    public static volatile SingularAttribute<Systemuser, Boolean> loggedIn;
    public static volatile SingularAttribute<Systemuser, String> password;

}