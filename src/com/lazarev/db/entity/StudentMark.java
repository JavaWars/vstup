package com.lazarev.db.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import com.lazarev.exception.MyAppException;

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
		if (mark<0){
			throw new MyAppException("incorrect input mark");
		}
		BigDecimal bd = new BigDecimal(mark).setScale(2, RoundingMode.HALF_EVEN);
		this.mark = bd.doubleValue();
	}

	@Override
	public String toString() {
		return "StudentMark [userId=" + userId + ", mark=" + mark + "]";
	}
	
}
