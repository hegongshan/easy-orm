package com.hegongshan.easy.orm.exception;

public class ParameterMismatchExcetion extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public ParameterMismatchExcetion(String message) {
		super(message);
	}
	
	public ParameterMismatchExcetion(String message,Throwable cause) {
		super(message,cause);
	}
}
