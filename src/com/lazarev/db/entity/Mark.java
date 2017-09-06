package com.lazarev.db.entity;

public class Mark extends Entity {

	private static final long serialVersionUID = -6641131080071260787L;

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
