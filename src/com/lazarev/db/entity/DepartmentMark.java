package com.lazarev.db.entity;

public class DepartmentMark extends Mark{

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
