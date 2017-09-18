package com.lazarev.web.servlets.user;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.lazarev.db.dao.DepartmentDAO;
import com.lazarev.db.dao.SubjectDAO;
import com.lazarev.db.entity.Department;
import com.lazarev.db.entity.DepartmentSubject;
import com.lazarev.web.Constants;

@WebServlet("/enter")
public class Enter extends HttpServlet {

	private static final Logger LOGGER = Logger.getLogger(Enter.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LOGGER.debug("Enter#doGet()");
		String idS = request.getParameter("id");

		if (idS != null) {
			int idDepartment = Integer.parseInt(idS);
			request.setAttribute("departmnetId", idS);
			Department department = new DepartmentDAO().get(idDepartment);
			List<DepartmentSubject> marks = new SubjectDAO().getAllSubjectsForDepartmentId(idDepartment);
			if (marks.size() > 0 && department != null) {
				request.setAttribute("department", department);
				request.setAttribute("marks", marks);
				request.getRequestDispatcher(Constants.PAGE_USER_SET_MARKS_FOR_DEPARTMENT).forward(request, response);
			}
			else{
				toError(request);
			}
		} else {
			toError(request);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LOGGER.debug("Enter#doPost()");

	}

	private void toError(HttpServletRequest request) {
		request.setAttribute("errorMessage", "can not enter to the incorrect department");
		request.getRequestDispatcher(Constants.PAGE_ERROR);

	}
}
