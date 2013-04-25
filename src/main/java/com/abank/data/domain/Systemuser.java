package com.abank.data.domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Logger;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Class to represent a user of the system
 * 
 * @author dgrupp
 */
@Entity
@NamedQueries({ @NamedQuery(name = Systemuser.QUERY_FIND_ALL, query = "SELECT b FROM Systemuser b"),
		@NamedQuery(name = Systemuser.QUERY_FIND_BY_ID, query = "SELECT b FROM Systemuser b where b.id = :id"),
		@NamedQuery(name = Systemuser.QUERYID_FIND_LOGIN, query = Systemuser.QUERYSQL_FIND_LOGIN)})
public class Systemuser implements BaseEntityIf, Serializable {

	private static final long serialVersionUID = -6796053568115238411L;

	public static final Logger logger = Logger.getLogger(Systemuser.class.getCanonicalName());

	public static final String QUERY_FIND_ALL = "Systemuser.findAll";
	public static final String QUERY_FIND_BY_ID = "Systemuser.findById";
	public static final String QUERYID_FIND_LOGIN = "Systemuser.findLoginData";
	public static final String QUERYSQL_FIND_LOGIN = "SELECT s FROM Systemuser s where s.login = :login and s.password = :password";

	/**
	 * ~~~ primary key ~~~
	 */

	@Id
	@SequenceGenerator(name = "SEQ_GEN_SYSTEMUSER", sequenceName = "SEQ_PK_SYSTEMUSER", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN_SYSTEMUSER")
	private Long id;

	/**
	 * ~~~ relationships ~~~
	 */
	
	// bi-directional relationship
//	@OneToOne
//	@JoinColumn(name="EMPLOYEE_ID")
//	Employee employee;

	/**
	 * ~~~ properties ~~~
	 */

	private String login;

	private String password;

	private Boolean loggedIn;

	@Temporal(TemporalType.DATE)
	private Calendar lastLoginDate;

	@Temporal(TemporalType.DATE)
	private Calendar lastLogoutDate;

	/**
	 * ~~~ constructors ~~~
	 */
	public Systemuser() {
	}

	/**
	 * ~~~ getters / setters ~~~
	 */

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the login
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * @param login
	 *            the login to set
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the loggedIn
	 */
	public Boolean getLoggedIn() {
		return loggedIn;
	}

	/**
	 * @param loggedIn
	 *            the loggedIn to set
	 */
	public void setLoggedIn(Boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	/**
	 * @return the lastLoginDate
	 */
	public Calendar getLastLoginDate() {
		return lastLoginDate;
	}

	/**
	 * @param lastLoginDate
	 *            the lastLoginDate to set
	 */
	public void setLastLoginDate(Calendar lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	/**
	 * @return the lastLogoutDate
	 */
	public Calendar getLastLogoutDate() {
		return lastLogoutDate;
	}

	/**
	 * @param lastLogoutDate
	 *            the lastLogoutDate to set
	 */
	public void setLastLogoutDate(Calendar lastLogoutDate) {
		this.lastLogoutDate = lastLogoutDate;
	}

	/**
	 * ~~~ general overwrites ~~~
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lastLoginDate == null) ? 0 : lastLoginDate.hashCode());
		result = prime * result + ((lastLogoutDate == null) ? 0 : lastLogoutDate.hashCode());
		result = prime * result + ((loggedIn == null) ? 0 : loggedIn.hashCode());
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		return result;
	}

	/**
	 * overwrite of the equals() method
	 * 
	 * @return
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Systemuser other = (Systemuser) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastLoginDate == null) {
			if (other.lastLoginDate != null)
				return false;
		} else if (!lastLoginDate.equals(other.lastLoginDate))
			return false;
		if (lastLogoutDate == null) {
			if (other.lastLogoutDate != null)
				return false;
		} else if (!lastLogoutDate.equals(other.lastLogoutDate))
			return false;
		if (loggedIn == null) {
			if (other.loggedIn != null)
				return false;
		} else if (!loggedIn.equals(other.loggedIn))
			return false;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		SimpleDateFormat SDF = new SimpleDateFormat("dd.MM.yyyy");
        String lastLoginDateString = (null == lastLoginDate) ? null : SDF.format(lastLoginDate.getTime());
        String lastLogoutDateString = (null == lastLogoutDate) ? null : SDF.format(lastLogoutDate.getTime());

		return "Systemuser [id=" + id + ", login=" + login + ", password=" + password + ", loggedIn=" + loggedIn + ", lastLoginDate="
				+ lastLoginDateString + ", lastLogoutDate=" + lastLogoutDateString + "]";
	}

}
