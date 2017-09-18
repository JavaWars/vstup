package com.lazarev.db.entity.extra;

import com.lazarev.db.entity.User;

public class UserTotalMark extends User{

	private static final long serialVersionUID = -7866760492802246599L;
	
	private double mark;

	public double getMark() {
		return mark;
	}

	public void setMark(double mark) {
		this.mark = mark;
	}

	@Override
	public String toString() {
		return "UserTotalMark [mark=" + mark + "]";
	}
	
}
