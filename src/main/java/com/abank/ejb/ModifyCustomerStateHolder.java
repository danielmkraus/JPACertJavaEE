package com.abank.ejb;

import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import com.abank.data.dao.CustomerDaoIf;
import com.abank.data.domain.Customer;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Stateful
@Dependent
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class ModifyCustomerStateHolder implements Serializable {

	private static final long serialVersionUID = 298578076103417468L;
    private static final SimpleDateFormat SDF = new SimpleDateFormat("dd.MM.yyyy");

    final static Logger logger = Logger.getLogger(ModifyCustomerStateHolder.class);

	@PersistenceContext(type = PersistenceContextType.EXTENDED, unitName = "JpaPU")
	EntityManager em;
	
	@Inject
	CustomerDaoIf customerDao;
	
	public Customer findCustomer(String firstname, String lastname, Calendar birthdate) {
        logger.debug("trying to find customer with firstname=" + firstname + ", lastname=" + lastname + ", birthdate=" + SDF.format(birthdate.getTime()));
		Customer customer = customerDao.findByFLB(firstname, lastname, birthdate);
        logger.debug("Found customer " + customer);
		return customer;
	}
	
	@Remove
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void mergeCustomer(Customer customer) {
		em.merge(customer);
	}

    @Remove
    public void cancel() {
    }

}
