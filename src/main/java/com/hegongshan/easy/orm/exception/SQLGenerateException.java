package com.hegongshan.easy.orm.exception;

public class SQLGenerateException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public SQLGenerateException(String message) {
		super(message);
	}
	
	public SQLGenerateException(String message,Throwable cause) {
		super(message,cause);
	}

}
