package com.lazarev.web.servlets.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.lazarev.web.Constants;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;

@WebServlet("/profile")
public class Profile extends HttpServlet {

	private static final Logger LOGGER=Logger.getLogger(Profile.class);
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LOGGER.debug("NewDepartment@doGet()");
		request.getRequestDispatcher(Constants.PAGE_USER_PROFILE).forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LOGGER.debug("NewDepartment@doGet()");

	}

}
