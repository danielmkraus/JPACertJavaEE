package com.abank.data.producers;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.Parameter;

import org.apache.log4j.Logger;

import com.abank.common.Credentials;
import com.abank.common.exceptions.NotLoggedInException;
import com.abank.data.dao.SystemuserDaoIf;
import com.abank.data.domain.Systemuser;
import com.abank.ejb.LoginService;

@SessionScoped
@Named
public class Login implements Serializable {

	private static final long serialVersionUID = 4900682153720015339L;

	final static Logger logger = Logger.getLogger(Login.class);

	@Inject
	Credentials credentials;

	@Inject
	SystemuserDaoIf userDao;
	
	@Inject
	LoginService logService;

	@SuppressWarnings("unused")
	private Parameter<String> usernameParam;
	@SuppressWarnings("unused")
	private Parameter<String> passwordParam;

	private Systemuser systemUser;

	public String login() {
		String login = credentials.getUsername();
		String password = credentials.getPassword();
		systemUser = logService.login(login, password);
		if (null != systemUser) {
			return "navigationOptions";
		} else {
			logger.info("Failed login for login " + credentials.getUsername());
			credentials.setUsername(null);
			credentials.setPassword(null);
			return null;
		}
	}

	public String logout() {
		logger.info(systemUser.toString() + " logging out");
		logService.logout(systemUser);
		systemUser = null;
		return "start";
	}

	public boolean isLoggedIn() {
		return systemUser != null;
	}

	@Produces
	@LoggedIn
	Systemuser getCurrentUser() {
		try {
			if (systemUser == null) {
				throw new NotLoggedInException();
			}
		} catch (NotLoggedInException e) {
			e.printStackTrace();
		}
		return systemUser;
	}
}