package com.abank.data.producers;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;

/**
 * Created by: gruppd, 09.02.13 15:05
 */
public class DataSource implements Serializable {

    private static final long serialVersionUID = -8725080658187948171L;

    @Produces
    @PersistenceContext(unitName="JpaPU")
    @BankPersUnit
    EntityManager userDatabaseEntityManager;

}

