package com.lazarev.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lazarev.service.UserService;
import org.apache.log4j.Logger;

import com.lazarev.db.Role;
import com.lazarev.db.dao.CityDAO;
import com.lazarev.db.dao.RoleDAO;
import com.lazarev.db.dao.UserDAO;
import com.lazarev.db.entity.User;
import com.lazarev.exception.MyAppException;
import com.lazarev.util.EmailService;
import com.lazarev.web.Constants;

@WebServlet("/registration")
public class Registration extends HttpServlet {

	private static Logger logger = Logger.getLogger(Registration.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.debug("registration doGet");

		Object role = request.getSession().getAttribute("ROLE");
		logger.debug("role= " + role);
		if (role == null) {

			request.getRequestDispatcher(Constants.PAGE_REGISTRATION).forward(request, response);

		} else {

			request.setAttribute("errorMessage", "You need logout");
			request.getRequestDispatcher(Constants.PAGE_ERROR).forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.debug("RegistrationdoPost()");

		UserService userService=new UserService();

		String email=request.getParameter("email");
		if (!userService.existUserEmail(email)) {

			logger.trace("user with email "+email+" will be tried insert to db");

			User insertedUser=userService.insertUser(request.getParameter("email"),
					request.getParameter("cityArea"),
					request.getParameter("fio"),
					request.getParameter("password"),
					request.getParameter("city"),
					request.getParameter("diplom"),
					request.getParameter("phone"));

			Role role = Role.USER;

			request.getSession().setAttribute("ROLE", role.getName());
			request.getSession().setAttribute("EMAIL", insertedUser.getEmail());
			request.getSession().setAttribute("NAME", insertedUser.getName());

			response.sendRedirect(Constants.COMMAND_HOME);
		} else {
			request.setAttribute("errorMessage", "this email already used ");
			request.getRequestDispatcher(Constants.PAGE_ERROR).forward(request, response);
		}
	}

}
