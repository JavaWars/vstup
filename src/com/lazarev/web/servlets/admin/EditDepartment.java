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
import com.lazarev.web.Constants;
import com.lazarev.web.json.JsonExtracter;
import com.lazarev.web.servlets.helper.Helper;

@WebServlet("/editDepartment")
public class EditDepartment extends HttpServlet {

	private static final Logger LOGGER = Logger.getLogger(EditDepartment.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		LOGGER.debug("Edit department#doGet()");
		req.setAttribute("isEditPage", true);
		String id=req.getParameter("id");
		LOGGER.trace("id="+id);
		req.setAttribute("id", id);
		req.getRequestDispatcher(Constants.PAGE_ADMIN_CREATE_NEW_DEPARTMENT).forward(req, resp);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LOGGER.debug("Edit department#doPost()");

		Department department = new Department();
		List<DepartmentSubject> marks = new LinkedList<>();
		JsonExtracter.extractExistingDepartmnet(Helper.getJsonQuery(request), department, marks);
		new DepartmentDAO().udateDepartment(department, marks);
	}

}
