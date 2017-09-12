package com.lazarev.db.entity;

import java.io.Serializable;

public class Base implements Serializable{
	
	private static final long serialVersionUID = 2449814243696094379L;
	
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
