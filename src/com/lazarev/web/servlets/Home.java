package com.lazarev.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.lazarev.db.Role;
import com.lazarev.web.Constants;

@WebServlet("/home")
public class Home extends HttpServlet {

	private static Logger logger = Logger.getLogger(Home.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.debug("SOMEBODY WANT SEE HOME PAGE");
		
		boolean roleConfirmed = true;
		Role role = null;
		
		Object oRole = request.getSession().getAttribute("ROLE");
		if (oRole != null) {
			// get role
			role = Role.valueOf((String) oRole);
		} else {
			roleConfirmed = false;
		}

		if (roleConfirmed) {
			switch (role) {
			case ADMIN:
				response.sendRedirect(Constants.COMMAND_ALL_DEPARTMENTS);
				break;
			case USER:
				response.sendRedirect(Constants.COMMAND_USER_PROFILE);
				break;
			default:
				roleConfirmed = false;
				break;
			}
		}
		if (!roleConfirmed) {
			logger.error(" Unknown user role. Redirect to login page");
			request.getSession().invalidate();
			response.sendRedirect(Constants.COMMAND_REGISTRATION);
		}
	}

}
