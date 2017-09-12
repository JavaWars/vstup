package com.lazarev.web.servlets.admin;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.lazarev.db.dao.DepartmentDAO;

@WebServlet("/delDepartment")
public class DeleteDepartment extends HttpServlet {

	private static final Logger LOGGER=Logger.getLogger(DeleteDepartment.class);
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LOGGER.debug("delDepartment doPost()");
		int depId=Integer.parseInt(request.getParameter("id"));
		new DepartmentDAO().delete(depId);
	}

}
