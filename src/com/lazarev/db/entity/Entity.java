package com.lazarev.db.entity;

import java.io.Serializable;

public class Entity implements Serializable{
	
	private static final long serialVersionUID = 1922082735744237775L;
	
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Entity [id=" + id + "]";
	}
}
