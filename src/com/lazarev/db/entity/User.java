package com.lazarev.db.entity;

public class User extends Entity{

	private static final long serialVersionUID = -7527022320520057512L;

	private String name;
	
	private String secondName;
	
	private String email;
	
	private String password;
	
	private int roleId;
	
	private int cityId;
	
	private String cityArea;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCityArea() {
		return cityArea;
	}

	public void setCityArea(String cityArea) {
		this.cityArea = cityArea;
	}

	
	
	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	@Override
	public String toString() {
		return "User [name=" + name + ", secondName=" + secondName + ", email=" + email + ", roleId=" + roleId
				+ ", cityId=" + cityId + ", cityArea=" + cityArea + "]";
	}
}
