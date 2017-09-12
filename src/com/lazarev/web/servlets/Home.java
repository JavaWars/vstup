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
import com.lazarev.web.servlets.operations.Operations;

@WebServlet("/home")
public class Home extends HttpServlet {

	private static Logger logger = Logger.getLogger(Home.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.debug("SOMEBODY WANT SEE HOME PAGE");
		boolean roleConfirmed = true;

		Object oRole = request.getSession().getAttribute("ROLE");
		Role role = null;
		if (oRole != null) {
			// get role
			role = Role.valueOf((String) oRole);
		} else {
			roleConfirmed = false;
		}

		request.getSession().setAttribute("operations",
				Operations.getAvalibleOperations(role, request.getContextPath()));

		if (role != null) {
			switch (role) {
			case ADMIN:
				response.sendRedirect(request.getContextPath() + Constants.COMMAND_ALL_DEPARTMENTS);

				break;

			case USER:
				response.sendRedirect(request.getContextPath() + Constants.COMMAND_USER_EDIT_PROFILE);

				break;
			default:
				roleConfirmed = false;
				break;
			}
		}
		if (!roleConfirmed) {
			logger.error(" Unknown user role. Redirect to login page");
			request.getSession().invalidate();
			response.sendRedirect(request.getContextPath() + Constants.PAGE_REGISTRATION);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
