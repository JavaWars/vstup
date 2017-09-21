package com.lazarev.db.entity.extra;

import com.lazarev.db.entity.User;

public class UserTotalMark extends User{
	
	private static final long serialVersionUID = -7466672210095749189L;

	private double mark;

	private String file;//imgData64
	
	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

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
