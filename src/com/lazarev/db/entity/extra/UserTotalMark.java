package com.lazarev.db.entity.extra;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.lazarev.db.entity.User;

public class UserTotalMark extends User{
	
	private static final long serialVersionUID = -7466672210095749189L;

	private double mark;

	private String file;//imgData64
	
	private int position;
		
	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

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
		BigDecimal bd = new BigDecimal(mark).setScale(2, RoundingMode.HALF_EVEN);
		this.mark = bd.doubleValue();
	}

	@Override
	public String toString() {
		return "UserTotalMark [mark=" + mark + "]";
	}
	
}
