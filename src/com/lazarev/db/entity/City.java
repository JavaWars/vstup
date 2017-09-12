package com.lazarev.db.entity;

public class City extends Base {

	private static final long serialVersionUID = -1107046733724111411L;
 
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "City [name=" + name + "]";
	}
	
	
	
}
