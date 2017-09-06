package com.lazarev.web.servlets;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.lazarev.db.Role;
import com.lazarev.db.dao.DepartmentDAO;
import com.lazarev.db.dao.UserDAO;
import com.lazarev.web.Constants;
import com.lazarev.web.servlets.operations.PossibleOperations;

@WebServlet("/departments")
public class Departments extends HttpServlet {

	private static final Logger LOGGER = Logger.getLogger(Departments.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LOGGER.debug("Department#doGet()");

		Object oRole = request.getSession().getAttribute("ROLE");
		Role role = null;
		if (oRole != null) {
			role = Role.valueOf((String) oRole);
		}
		request.setAttribute("departmens", new DepartmentDAO().getAll());
		if (role != null) {
			List<PossibleOperations> operations=new LinkedList<>();
			switch (role) {
			case ADMIN:
				
				operations.add(new PossibleOperations("delete","/delete"));
				operations.add(new PossibleOperations("edit","/edit"));

				break;

			case USER:
				operations.add(new PossibleOperations("send resume","/choose"));

				break;
			}
			request.setAttribute("oper", operations);
		}
		request.getRequestDispatcher(Constants.PAGE_DEPARTMENTS).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
