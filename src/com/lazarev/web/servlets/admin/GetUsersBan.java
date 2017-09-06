package com.lazarev.web.servlets.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.lazarev.db.dao.UserDAO;
import com.lazarev.db.entity.User;
import com.lazarev.web.Constants;

@WebServlet("/bannedUser")
public class GetUsersBan extends HttpServlet {

	private static final Logger LOGGER = Logger.getLogger(GetUsersBan.class);

	// return pages with banned users
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LOGGER.debug("UsersBan#doGet() get banned users");

		List<User> users = new UserDAO().getAllBanned();

		request.setAttribute("users", users);
		request.setAttribute("action", "unblock");
		request.setAttribute("href", request.getContextPath() + "/unblockUser");

		request.getRequestDispatcher(Constants.PAGE_ADMIN_ALL_USERS).forward(request, response);

	}

}
