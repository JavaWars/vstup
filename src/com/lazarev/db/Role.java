package com.lazarev.db;

public enum Role {
	
	USER("USER"), ADMIN("ADMIN");
	
	Role(String name){
		this.name=name;
	}
	
	private String name;
	
	public String getName(){
		return name;
	}
	
}
