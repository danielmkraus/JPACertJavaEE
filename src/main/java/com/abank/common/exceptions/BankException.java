package com.abank.common.exceptions;

/**
 * Top level exception of application.
 * 
 * @author dgrupp
 *
 */
public abstract class BankException extends Exception {

	private static final long serialVersionUID = -8202867824212547076L;

	/**
	 * default constructor - see API doc java.lang.Exception
	 */
	public BankException() {
		super ();
	}
	
	/**
	 * @param message - see API doc java.lang.Exception
	 */
	public BankException(String message) {
		super (message);
	}
	
	/**
	 * @param message - see API doc java.lang.Exception
	 * @param cause see API doc java.lang.Exception
	 */
	public BankException(String message, Throwable cause) {
		super (message, cause);
	}

	/**
	 * @param cause - see API doc java.lang.Exception
	 */
	public BankException(Throwable cause) {
		super (cause);
	}
}