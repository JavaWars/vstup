package com.lazarev.web.servlets.operations;

import java.io.Serializable;

public class PossibleOperations implements Serializable {

	private static final long serialVersionUID = -6783554753747943571L;

	public String operation;
	
	public String path;
	
	public PossibleOperations() {
	
	}

	public PossibleOperations(String operation, String path) {
		this.operation = operation;
		this.path = path;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public String toString() {
		return "PossibleOperations [operation=" + operation + ", path=" + path + "]";
	}
	
	
}
