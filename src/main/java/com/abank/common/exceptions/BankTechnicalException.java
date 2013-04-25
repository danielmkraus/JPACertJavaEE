package com.abank.common.exceptions;

/**
 * Parent for all technical exceptions of the application. Only subclasses shall be able to instantiate.
 * 
 * @author dgrupp
 *
 */
public abstract class BankTechnicalException extends BankException {

	private static final long serialVersionUID = 6662749961426859012L;

	/**
	 * default constructor - see API doc java.lang.Exception
	 */
	public BankTechnicalException() {
		super ();
	}
	
	/**
	 * @param message - see API doc java.lang.Exception
	 */
	public BankTechnicalException(String message) {
		super (message);
	}
	
	/**
	 * @param message - see API doc java.lang.Exception
	 * @param cause see API doc java.lang.Exception
	 */
	public BankTechnicalException(String message, Throwable cause) {
		super (message, cause);
	}

	/**
	 * @param cause - see API doc java.lang.Exception
	 */
	public BankTechnicalException(Throwable cause) {
		super (cause);
	}

}
