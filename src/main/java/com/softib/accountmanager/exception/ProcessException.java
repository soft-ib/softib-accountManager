package com.softib.accountmanager.exception;

public class ProcessException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -428300347805526006L;

	public ProcessException(String operation, Exception e) {
		super("Internal problem has occured while " + operation + "Error :  " + e.getMessage());

	}

}
