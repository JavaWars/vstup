package com.lazarev.web;

public class Constants {

	// pages
	public static final String PAGE_LOGIN = "/pages/login.jsp";
	public static final String PAGE_ERROR = "/pages/error.jsp";
	public static final String PAGE_ACCESS_ERROR = "/pages/securityError.jsp";
	public static final String PAGE_REGISTRATION = "/pages/registration.jsp";
	public static final String PAGE_DEPARTMENTS = "/pages/departments.jsp";
	
	//admin pages
	public static final String PAGE_ADMIN_CREATE_NEW_DEPARTMENT = "/pages/adminPage/new_department.jsp";
	public static final String PAGE_ADMIN_ALL_USERS = "/pages/adminPage/usersList.jsp";
	public static final String PAGE_ADMIN_STAT = "/pages/adminPage/adminStat.jsp";
	
	//userPageds
	public static final String PAGE_USER_STAT = "/pages/userPage/userStatistic.jsp";
	public static final String PAGE_USER_PROFILE = "/pages/userPage/profile.jsp";


	//COMMAND=path to servlet
	//common command
	public static final String COMMAND_HOME = "/home";
	public static final String COMMAND_LOGOUT = "/logout";
	public static final String COMMAND_ALL_DEPARTMENTS = "/departments";
	
	//admin command
	public static final String COMMAND_ADMIN_CREATE_NEW_DEPARTMENT = "/newDepartment";
	public static final String COMMAND_ADMIN_STATISTIC = "/adminStat";
	public static final String COMMAND_ADMIN_GET_USERS = "/cleanUser";//not banned users
	public static final String COMMAND_ADMIN_GET_BLOCKED_USERS = "/bannedUser";
	public static final String COMMAND_ADMIN_DELETE_DEPARTMNET = "/delDepartment";
	public static final String COMMAND_ADMIN_EDIT_DEPARTMENT = "/editDepartment";
	
	//user command
	public static final String COMMAND_USER_EDIT_PROFILE = "/profile";
	public static final String COMMAND_USER_STATISTIC = "/userStat";
	public static final String COMMAND_USER_DEPARTMENTS = "/department";
	
	
}
