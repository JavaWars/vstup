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

	private Logger logger = Logger.getLogger(Home.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.debug(" SOMEBODY WANT SEE HOME PAGE ");
		boolean roleConfirmed = true;

		Object oRole = request.getSession().getAttribute("ROLE");
		Role role = null;
		if (oRole != null) {
			// get role
			role = Role.valueOf((String) oRole);
		} else {
			roleConfirmed = false;
		}
		if (request.getSession() != null) {
			request.getSession().setAttribute("operations",
					Operations.getAvalibleOperations(role, request.getContextPath()));
		}
		if (role != null) {
			logger.trace("user with role= " + role + "will be redirect");
			switch (role) {
			case ADMIN:
				response.sendRedirect(request.getContextPath() + Constants.PAGE_HOME_ADMIN);

				break;

			case USER:
				response.sendRedirect(request.getContextPath() + Constants.PAGE_HOME_USER);

				break;

			case SUPERADMIN:
				response.sendRedirect(request.getContextPath() + Constants.PAGE_HOME_SUPER_ADMIN);

				break;
			default:
				logger.error(" Unnown user role redirect to login");
				roleConfirmed = false;
				break;
			}
		}
		if (!roleConfirmed) {
			logger.debug("CAN not show home page");
			request.getSession().invalidate();
			response.sendRedirect(request.getContextPath() + Constants.PAGE_REGISTRATION);
			// request.getRequestDispatcher(Constants.PAGE_LOGIN).forward(request,
			// response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
