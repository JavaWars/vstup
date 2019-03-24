package com.lazarev.web.servlets.admin;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.lazarev.db.dao.DepartmentDAO;
import com.lazarev.db.entity.Department;
import com.lazarev.db.entity.DepartmentSubject;
import com.lazarev.exception.MyAppException;
import com.lazarev.service.DepartmentService;
import com.lazarev.web.Constants;
import com.lazarev.web.json.JsonExtracter;
import com.lazarev.web.servlets.helper.Helper;

@WebServlet("/newDepartment")
public class NewDepartment extends HttpServlet {

	private static final Logger LOGGER = Logger.getLogger(NewDepartment.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LOGGER.debug("NewDepartment@doGet()");
		request.getRequestDispatcher(Constants.PAGE_ADMIN_CREATE_NEW_DEPARTMENT).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LOGGER.debug("NewDepartment@doPost()");

		Department department = new Department();
		List<DepartmentSubject> marks = new LinkedList<>();
		JsonExtracter.extractDepartmnet(Helper.getJsonQuery(request), department, marks);
		try {
			new DepartmentService().insertDepWithMarks(department, marks);
		} catch (MyAppException e) {
			LOGGER.error(e.getMessage());
			response.getWriter().append(e.getMessage());
			response.setStatus(500);
		}
	}

}
