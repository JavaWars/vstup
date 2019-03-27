package com.lazarev.web.servlets.user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.lazarev.db.entity.Phase;
import com.lazarev.service.PhaseService;
import com.lazarev.service.UserService;
import com.lazarev.web.servlets.superadmin.PhaseServlet;

@WebServlet("/UserUpdateBaseInfo")
public class UserUpdateBaseInfo extends HttpServlet {

	private static final Logger LOGGER = Logger.getLogger(UserUpdateBaseInfo.class);

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		LOGGER.trace(
				"UserUpdateBaseInfo.doPost()" + request.getParameter("fio") + "|" + request.getParameter("diplom"));
		String fio = request.getParameter("fio");
		String diplom = request.getParameter("diplom");
		String email = (String) request.getSession().getAttribute("EMAIL");

		if (PhaseService.getCurrentPhase() == Phase.DOCUMENT_SERVE)
			new UserService().updateUserData(email, fio, diplom);
		else {
			response.setStatus(500);
			response.getWriter().append("impossible update user information in current phase try later");
		}
	}

}
