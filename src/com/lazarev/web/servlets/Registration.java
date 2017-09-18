package com.lazarev.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.lazarev.db.Role;
import com.lazarev.db.dao.CityDAO;
import com.lazarev.db.dao.RoleDAO;
import com.lazarev.db.dao.UserDAO;
import com.lazarev.db.entity.User;
import com.lazarev.util.EmailService;
import com.lazarev.web.Constants;

@WebServlet("/registration")
public class Registration extends HttpServlet {

	private static Logger logger = Logger.getLogger(Registration.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.debug("registration doGet");

		Object role = request.getSession().getAttribute("ROLE");
		logger.debug("role= "+role);
		if (role == null) {

			request.getRequestDispatcher(Constants.PAGE_REGISTRATION).forward(request, response);
		
		} else {
			
			request.setAttribute("errorMessage", "You need logout");
			request.getRequestDispatcher( Constants.PAGE_ERROR).forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.debug("RegistrationdoPost()");

		User newUser = new User();
		newUser.setEmail(request.getParameter("email"));
		newUser.setCityArea(request.getParameter("cityArea"));
		newUser.setName(request.getParameter("name"));
		newUser.setSecondName(request.getParameter("secondName"));
		newUser.setPassword(request.getParameter("password"));
		Role role = Role.USER;
		newUser.setRoleId(new RoleDAO().getRoleIdByRoleName(role.getName()));
		newUser.setCityId(new CityDAO().get(request.getParameter("city")));
		logger.trace("newUser" + newUser);
		
		new UserDAO().insert(newUser);
		
		request.getSession().setAttribute("ROLE", role.getName());
		request.getSession().setAttribute("EMAIL", newUser.getEmail());
		request.getSession().setAttribute("NAME",newUser.getName());
		
		EmailService.getInstance().sendMessage(newUser.getEmail(), "Welcome in vstup system", "welcome my dear user description");
		
		response.sendRedirect(Constants.COMMAND_HOME);
	}

}
