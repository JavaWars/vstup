package com.lazarev.db.entity;

public class Subject extends Base {

	private static final long serialVersionUID = -2901013318005847389L;
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Mark [name=" + name + "]";
	}
	
}
