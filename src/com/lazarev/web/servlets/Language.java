package com.lazarev.web.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.lazarev.web.Constants;

@WebServlet("/language")
public class Language extends HttpServlet {

	private static final Logger LOGGER = Logger.getLogger(Language.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LOGGER.debug("Language#doGet()");
		request.getRequestDispatcher(Constants.PAGE_LANGUAGE).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LOGGER.debug("Language#doPost()");
		String lang=request.getParameter("language");
		LOGGER.trace("user select language"+lang);
		request.getSession().setAttribute("LANG", lang);
		response.sendRedirect(Constants.COMMAND_HOME);
	}

}
