package com.abank.data.domain;

import com.abank.data.domain.Employee;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2013-03-25T20:36:36")
@StaticMetamodel(Project.class)
public class Project_ { 

    public static volatile SingularAttribute<Project, Long> id;
    public static volatile SingularAttribute<Project, String> name;
    public static volatile ListAttribute<Project, Employee> employees;

}