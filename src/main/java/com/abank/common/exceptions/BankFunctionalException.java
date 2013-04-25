package com.abank.common.exceptions;

/**
 * Parent for all functional exceptions of the application. Only subclasses shall be able to instantiate.
 * 
 * @author dgrupp
 *
 */
public abstract class BankFunctionalException extends BankException {

	private static final long serialVersionUID = 6437054831433330757L;

	/**
	 * default constructor - see API doc java.lang.Exception
	 */
	public BankFunctionalException() {
		super ();
	}
	
	/**
	 * @param message - see API doc java.lang.Exception
	 */
	public BankFunctionalException(String message) {
		super (message);
	}
	
	/**
	 * @param message - see API doc java.lang.Exception
	 * @param cause see API doc java.lang.Exception
	 */
	public BankFunctionalException(String message, Throwable cause) {
		super (message, cause);
	}

	/**
	 * @param cause - see API doc java.lang.Exception
	 */
	public BankFunctionalException(Throwable cause) {
		super (cause);
	}
	
}

