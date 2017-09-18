package com.lazarev.db.entity;

public class StudentMark extends Subject{
	
	private static final long serialVersionUID = -2850096538588313673L;

	private int userId;
	
	private double mark;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public double getMark() {
		return mark;
	}

	public void setMark(double mark) {
		this.mark = mark;
	}

	@Override
	public String toString() {
		return "UserMark [userId=" + userId + ", mark=" + mark + "]";
	}
	
}
