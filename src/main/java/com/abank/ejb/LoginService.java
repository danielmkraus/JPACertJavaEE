package com.abank.ejb;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import com.abank.data.dao.SystemuserDaoIf;
import com.abank.data.domain.Systemuser;

@Stateless
public class LoginService implements Serializable {
	
	final static Logger logger = Logger.getLogger(LoginService.class);
	
	@Inject
	SystemuserDaoIf systemUserDao;
	
	public Systemuser login(String login, String password) {
		Systemuser loggedInUser = null;
		List<Systemuser> results = systemUserDao.findLoginPwd(login, password);
		if (!results.isEmpty()) {
			loggedInUser = results.get(0);
			loggedInUser.setLastLoginDate(Calendar.getInstance());
			loggedInUser.setLoggedIn(true);
			logger.info(loggedInUser.toString() + " logged in");
			return loggedInUser;
		} else {
			return null;
		}
	}
	
	public void logout(Systemuser user) {
		Systemuser systemUser = systemUserDao.findById(user.getId());
		systemUser.setLastLogoutDate(Calendar.getInstance());
		systemUser.setLoggedIn(false);
	}

}
