package com.lazarev.web.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.lazarev.db.Sorters.DepartmentSorter;
import com.lazarev.db.dao.DepartmentDAO;
import com.lazarev.db.entity.Department;
import com.lazarev.web.Constants;

@WebServlet("/departments")
public class Departments extends HttpServlet {

	private static final Logger LOGGER = Logger.getLogger(Departments.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LOGGER.debug("Department#doGet()");
		String sortType = request.getParameter("sort");
		LOGGER.info("sort " + sortType);
		String departmantName = request.getParameter("departmentName");
		LOGGER.info("departmentName " + departmantName);

		List<Department> departments = null;
		if (departmantName != null && departmantName.length() > 0) {
			departments = new DepartmentDAO().getAllDepartmnetsWithName(departmantName);
		} else {
			departments = new DepartmentDAO().getAll();
		}
		request.setAttribute("departmens", DepartmentSorter.sort(sortType, departments));
		request.getRequestDispatcher(Constants.PAGE_DEPARTMENTS).forward(request, response);
	}

}
