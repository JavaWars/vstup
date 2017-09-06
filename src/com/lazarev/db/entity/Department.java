package com.lazarev.db.entity;

public class Department extends Entity {

	private static final long serialVersionUID = -2310242248201654120L;

	private String name;

	private int totaPlace;

	private int placeDov;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTotaPlace() {
		return totaPlace;
	}

	public void setTotaPlace(int totaPlace) {
		this.totaPlace = totaPlace;
	}

	public int getPlaceDov() {
		return placeDov;
	}

	public void setPlaceDov(int placeDov) {
		this.placeDov = placeDov;
	}

	@Override
	public String toString() {
		return "Department [name=" + name + ", totaPlace=" + totaPlace + ", placeDov=" + placeDov + "]";
	}

}
