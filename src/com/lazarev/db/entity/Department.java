package com.lazarev.db.entity;

public class Department extends Base {

	private static final long serialVersionUID = 5704784936415927250L;

	private String name;

	private int totaPlace;

	private int placeGov;

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

	public int getPlaceGov() {
		return placeGov;
	}

	public void setPlaceGov(int placeGov) {
		this.placeGov = placeGov;
	}

	@Override
	public String toString() {
		return "Department [name=" + name + ", totaPlace=" + totaPlace + ", placeDov=" + placeGov + "]";
	}

}
