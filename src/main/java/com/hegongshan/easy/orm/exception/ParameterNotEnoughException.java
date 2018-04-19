package com.hegongshan.easy.orm.exception;

public class ParameterNotEnoughException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public ParameterNotEnoughException(String message) {
		super(message);
	}
	
	public ParameterNotEnoughException(String message,Throwable cause) {
		super(message,cause);
	}
}
