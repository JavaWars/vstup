package com.lazarev.exception;

public class MyDbException extends Exception{

	public MyDbException() {
		super();
	}

	public MyDbException(String message, Throwable cause) {
		super(message, cause);
	}

	public MyDbException(String message) {
		super(message);
	}

	public MyDbException(Throwable cause) {
		super(cause);
	}

}
