package com.lazarev.exception;

public class MyAppException extends Exception{

	public MyAppException() {
		super();
	}

	public MyAppException(String message, Throwable cause) {
		super(message, cause);
	}

	public MyAppException(String message) {
		super(message);
	}

	public MyAppException(Throwable cause) {
		super(cause);
	}

}
