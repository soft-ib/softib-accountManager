package com.softib.accountmanager.exception;

public class EntityNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3611789741642710092L;

	public EntityNotFoundException(String type, Integer id) {
		super("Could not find " + type + " with id :" + id);
	}

}
