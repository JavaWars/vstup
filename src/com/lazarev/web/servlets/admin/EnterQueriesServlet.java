package com.lazarev.web.servlets.admin;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.lazarev.web.Constants;

@WebServlet("/enterQueries")
public class EnterQueriesServlet extends HttpServlet {

	private static final Logger LOGGER=Logger.getLogger(EnterQueriesServlet.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LOGGER.trace("enterQueries.doGet()");
		request.getRequestDispatcher(Constants.PAGE_ADMIN_ENTER_QUERIES).forward(request, response);
	}

//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//	
//	}

}
