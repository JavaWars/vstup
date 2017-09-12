package com.lazarev.db.entity;

public class DepartmentSubject extends Subject{
	
	private static final long serialVersionUID = -4611888461646766600L;
	
	private double scale;

	public double getScale() {
		return scale;
	}

	public void setScale(double scale) {
		this.scale = scale;
	}

	@Override
	public String toString() {
		return "DepartmentMark [scale=" + scale + "]";
	}
	
}
