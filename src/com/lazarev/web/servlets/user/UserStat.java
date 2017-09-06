package com.lazarev.web.servlets.user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.lazarev.web.Constants;

@WebServlet("/userStat")
public class UserStat extends HttpServlet {
	
	private static final Logger LOGGER=Logger.getLogger(UserStat.class);
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	LOGGER.debug("userStat#doGet()");
	request.getRequestDispatcher(Constants.PAGE_USER_STAT).forward(request, response);
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
