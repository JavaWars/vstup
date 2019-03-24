package com.lazarev.db.entity;

public class DepartmentSubject extends Subject{
	
	private static final long serialVersionUID = -4611888461646766600L;
	
	private double scale;
	private double maxMark;
	private boolean userEntered;

	public double getScale() {
		return scale;
	}

	public void setScale(double scale) {
		this.scale = scale;
	}

	public double getMaxMark() {
		return maxMark;
	}

	public void setMaxMark(double maxMark) {
		this.maxMark = maxMark;
	}

	public boolean getUserEntered() {
		return userEntered;
	}

	public void setUserEntered(boolean isUserEntered) {
		this.userEntered = isUserEntered;
	}

	
	@Override
	public String toString() {
		return "DepartmentMark [scale=" + scale + "]";
	}
	
}
