package com.lazarev.db.entity;

import com.lazarev.exception.MyAppException;
import org.apache.log4j.Logger;

import java.sql.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User extends Base{
	private static Logger logger = Logger.getLogger(User.class);

	private static final long serialVersionUID = -7527022320520057512L;

	private String fio;
	private String email;
	private String diplom;
	private String phone;
	private String name;
	private String secondName;
	private String password;
	private String cityArea;
	private Date birthday;

	private int roleId;
	private int cityId;
	private int departmentCount;

	public int getDepartmentCount() {
		return departmentCount;
	}

	public void setDepartmentCount(int departmentCount) {
		this.departmentCount = departmentCount;
	}

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
		if (password.length()<6){
			logger.info("password is too short. (length="+password.length()+"char, minimum 6 needed)");
			//throw  new MyAppException("Password is so short");
		}
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

	public String getFio() {
		return fio;
	}

	public void setFio(String fio) {
		if ((fio.split(" ").length<3) || (fio.split(" ").length>4)){throw new MyAppException("fio MUST contain 3 part ()");}
		String buf[]=fio.split(" ");

		this.secondName=buf[0];
		this.name=buf[1];
		this.fio = buf[0]+" "+buf[1]+" "+buf[2];
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getDiplom() {
		return diplom;
	}

	public void setDiplom(String diplom) {
		String regex = "(.*)(\\d+)(.*)";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher=pattern.matcher(diplom);
		if (!matcher.matches()){throw new MyAppException("Diplom is invalid");}
		this.diplom = diplom;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "User [fio=" + fio + ", email=" + email + ", diplom=" + diplom + ", phone=" + phone + ", name=" + name
				+ ", secondName=" + secondName + ", password=" + password + ", cityArea=" + cityArea + ", birthday="
				+ birthday + ", roleId=" + roleId + ", cityId=" + cityId + ", departmentCount=" + departmentCount + "]";
	}

	public void setBirthday(String birthday) {
		String arr[]=birthday.split("-");
		Date  d=new Date(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]), Integer.parseInt(arr[2]));
		this.setBirthday(d);
	}

}
