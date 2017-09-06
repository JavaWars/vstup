package com.lazarev.web.servlets.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.lazarev.web.Constants;

@WebServlet("/newDepartment")
public class NewDepartment extends HttpServlet {

	private static final Logger LOGGER = Logger.getLogger(NewDepartment.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LOGGER.debug("NewDepartment@doGet()");
		request.getRequestDispatcher(Constants.PAGE_ADMIN_CREATE_NEW_DEPARTMENT).forward(request, response);;

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LOGGER.debug("NewDepartment@doPost()");

		
	}

}
