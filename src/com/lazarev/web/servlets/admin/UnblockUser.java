package com.lazarev.web.servlets.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.lazarev.db.dao.UserDAO;

@WebServlet("/unblockUser")
public class UnblockUser extends HttpServlet {

	private static final Logger LOGGER = Logger.getLogger(UnblockUser.class);

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LOGGER.debug("UnblockUser@doPost()");

		Object id = request.getParameter("id");
		LOGGER.trace("id(ob)" + id);
		if (id != null) {
			int userId = Integer.parseInt(request.getParameter("id"));
			new UserDAO().unBlockUser(userId);
			LOGGER.trace("admin want bant ban user" + userId);
		}
//		response.sendRedirect("/vstup/bannedUser");
	}

}
