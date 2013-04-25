package com.abank.data.dao;

import java.util.Calendar;

import com.abank.data.domain.Customer;

/**
 * Created by: gruppd, 09.02.13 15:13
 */
public interface CustomerDaoIf extends BaseDaoIf<Customer> {

	Customer findByFLB(String firstname, String lastname, Calendar birthdate);
	
}
