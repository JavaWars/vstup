package com.lazarev.db.entity;

public class UserMark extends Mark{
	
	private static final long serialVersionUID = 5783974656004616760L;

	private int userId;
	
	private int mark;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getMark() {
		return mark;
	}

	public void setMark(int mark) {
		this.mark = mark;
	}

	@Override
	public String toString() {
		return "UserMark [userId=" + userId + ", mark=" + mark + "]";
	}
	
}
