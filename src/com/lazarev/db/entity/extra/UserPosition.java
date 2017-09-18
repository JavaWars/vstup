package com.lazarev.db.entity.extra;

import com.lazarev.db.entity.Department;

public class UserPosition extends Department {

	private static final long serialVersionUID = 3062162506098459953L;

	private int myPlace;

	private int totalPeople;

	public int getMyPlace() {
		return myPlace;
	}

	public void setMyPlace(int myPlace) {
		this.myPlace = myPlace;
	}

	public int getTotalPeople() {
		return totalPeople;
	}

	public void setTotalPeople(int totalPeople) {
		this.totalPeople = totalPeople;
	}

	@Override
	public String toString() {
		return "UserPosition [myPlace=" + myPlace + ", totalPeople=" + totalPeople + "]";
	}
}
