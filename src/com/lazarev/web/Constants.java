package com.lazarev.web;

public class Constants {

	// pages
	public static final String PAGE_LOGIN = "/pages/login.jsp";
	public static final String PAGE_ERROR = "/pages/error.jsp";
	public static final String PAGE_ACCESS_ERROR = "/pages/securityError.jsp";
	public static final String PAGE_REGISTRATION = "/pages/registration.jsp";
	public static final String PAGE_DEPARTMENTS = "/pages/departments.jsp";
	public static final String PAGE_DEPARTMENT_RATING = "/pages/departmentRating.jsp";
	public static final String PAGE_LANGUAGE = "/pages/language.jsp";
	//admin pages
	public static final String PAGE_ADMIN_CREATE_NEW_DEPARTMENT = "/pages/adminPage/new_department.jsp";
	public static final String PAGE_ADMIN_ALL_USERS = "/pages/adminPage/usersList.jsp";
	public static final String PAGE_ADMIN_STAT = "/pages/adminPage/adminStat.jsp";
	
	//userPages
	public static final String PAGE_USER_STAT = "/pages/userPage/userStatistic.jsp";
	public static final String PAGE_USER_PROFILE = "/pages/userPage/profile.jsp";
	public static final String PAGE_USER_SET_MARKS_FOR_DEPARTMENT = "/pages/userPage/marks.jsp";

	//COMMAND=path to servlet
	//common command
	public static final String COMMAND_LOGIN = "login";
	public static final String COMMAND_HOME = "home";
	public static final String COMMAND_REGISTRATION = "registration";
	public static final String COMMAND_LOGOUT = "logout";
	public static final String COMMAND_ALL_DEPARTMENTS = "departments";
	
	//user command
	public static final String COMMAND_USER_PROFILE = "userMark";
	
	public static final String PDF_HOME="C:\\data\\pdf\\";
	public static final String IMG_HOME="C:\\data\\img\\";
}
