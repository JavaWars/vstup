package com.lazarev.db.entity;

public enum Phase {
	PRE_REGISTRATION("PRE_REGISTRATION"), DOCUMENT_SERVE("DOCUMENT_SERVE"),RESULT_CHECKING("RESULT_CHECKING");
	
	Phase(String name){
		this.name=name;
	}
	
	private String name;
	
	public String getName(){
		return name;
	}
	
}
