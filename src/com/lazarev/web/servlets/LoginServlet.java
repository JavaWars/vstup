package com.lazarev.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.lazarev.db.entity.Role;
import com.lazarev.db.dao.RoleDAO;
import com.lazarev.db.dao.UserDAO;
import com.lazarev.db.entity.User;
import com.lazarev.web.Constants;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

	private static Logger logger = Logger.getLogger(LoginServlet.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.debug("LoginServlet#doGet()");
		
		Object role = request.getSession().getAttribute("ROLE");
		logger.debug("role= "+role);
		if (role == null) {
			
			request.getRequestDispatcher(Constants.PAGE_LOGIN).forward(request, response);
		
		} else {
			
			request.setAttribute("errorMessage", "You need logout");
			request.getRequestDispatcher( Constants.PAGE_ERROR).forward(request, response);
		}
	}

	// check user from db if exist
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		logger.debug("");
		
		User user = new User();

		user.setPassword(request.getParameter("password"));

		user.setEmail(request.getParameter("email"));
		
		UserDAO userDB = new UserDAO();

		boolean correct = userDB.ckeck(user);

		if (correct) {
			logger.trace("CORRECT DATA");
			
			Role role = new RoleDAO().get(user.getRoleId());
			
			logger.trace("Set ROLE"+role.getName());
			request.getSession().setAttribute("ROLE", role.getName());
			request.getSession().setAttribute("EMAIL", user.getEmail());
			request.getSession().setAttribute("NAME",user.getName());

			logger.trace("redirecting to "+Constants.COMMAND_HOME);
			response.sendRedirect(Constants.COMMAND_HOME);
			//redirect to home
		} else {
			logger.trace("redirecting to "+Constants.PAGE_ERROR);
			request.setAttribute("errorMessage", "incorrect email or password");
			request.getRequestDispatcher(Constants.PAGE_ERROR).forward(request, response);
		}


	}

}
