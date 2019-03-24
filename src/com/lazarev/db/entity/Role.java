package com.lazarev.db.entity;

public enum Role {
	
	USER("USER"), ADMIN("ADMIN"),SUPERADMIN("SUPERADMIN");
	
	Role(String name){
		this.name=name;
	}
	
	private String name;
	
	public String getName(){
		return name;
	}
	
}
