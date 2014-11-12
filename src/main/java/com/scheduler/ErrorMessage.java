package com.scheduler;

public class ErrorMessage extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6845809413210486434L;

	public ErrorMessage(String error) {
		super(error);
		
	}
}
