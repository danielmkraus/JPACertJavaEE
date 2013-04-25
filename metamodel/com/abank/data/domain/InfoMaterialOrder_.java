package com.abank.data.domain;

import com.abank.data.domain.InfoMaterial;
import com.abank.data.domain.Systemuser;
import java.util.Calendar;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2013-03-25T20:36:36")
@StaticMetamodel(InfoMaterialOrder.class)
public class InfoMaterialOrder_ { 

    public static volatile SingularAttribute<InfoMaterialOrder, Long> id;
    public static volatile SingularAttribute<InfoMaterialOrder, InfoMaterial> infoMaterial;
    public static volatile SingularAttribute<InfoMaterialOrder, Calendar> orderDate;
    public static volatile SingularAttribute<InfoMaterialOrder, Integer> quantity;
    public static volatile SingularAttribute<InfoMaterialOrder, Systemuser> systemUser;
    public static volatile SingularAttribute<InfoMaterialOrder, Integer> version;

}