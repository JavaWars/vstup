package com.lazarev.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.lazarev.web.Constants;

@WebServlet("/logout")
public class Logout extends HttpServlet {

	private Logger logger=Logger.getLogger(Logout.class);
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.debug("User create LOGOUT action");
		request.getSession().invalidate();
		response.sendRedirect(request.getContextPath()+Constants.PAGE_LOGIN);
	}

}
