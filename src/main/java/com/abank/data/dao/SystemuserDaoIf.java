package com.abank.data.dao;

import java.util.List;

import com.abank.data.domain.Systemuser;

/**
 * Created by: gruppd, 09.02.13 15:13
 */
public interface SystemuserDaoIf extends BaseDaoIf<Systemuser> {

	List<Systemuser> findLoginPwd(String login, String pwd);

}
