package com.abank.common.exceptions;

/**
 * Exception to be thrown in case a user of the system is not logged in
 * @author dgrupp
 *
 */
public class NotLoggedInException extends BankFunctionalException {

	private static final long serialVersionUID = -7874801449167058623L;

	/**
	 * default constructor - see API doc java.lang.Exception
	 */
	public NotLoggedInException() {
		super ();
	}
	
	/**
	 * @param message - see API doc java.lang.Exception
	 */
	public NotLoggedInException(String message) {
		super (message);
	}
	
	/**
	 * @param message - see API doc java.lang.Exception
	 * @param cause see API doc java.lang.Exception
	 */
	public NotLoggedInException(String message, Throwable cause) {
		super (message, cause);
	}

	/**
	 * @param cause - see API doc java.lang.Exception
	 */
	public NotLoggedInException(Throwable cause) {
		super (cause);
	}
	
}
