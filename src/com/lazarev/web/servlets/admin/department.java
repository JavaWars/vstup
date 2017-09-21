package com.lazarev.web.servlets.admin;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import com.lazarev.web.json.JsonPacker;

@WebServlet("/department")
public class department extends HttpServlet {

	private static final Logger LOGGER = Logger.getLogger(department.class);

	// get existing department
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LOGGER.debug("department#doGet()");

		String sId = request.getParameter("id");
		if (sId != null) {
			int departmentId = Integer.parseInt(sId);
			//return json format
			response.getWriter().print(JsonPacker.packExistingDeprtment(departmentId));
		}
	}

	// edit existing department
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LOGGER.debug("department#doPost()");
		
	}

}
