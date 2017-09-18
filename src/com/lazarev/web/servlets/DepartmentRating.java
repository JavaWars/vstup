package com.lazarev.web.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.lazarev.db.dao.UserPositionDAO;
import com.lazarev.db.entity.extra.UserTotalMark;
import com.lazarev.web.Constants;

@WebServlet("/departmentRating")
public class DepartmentRating extends HttpServlet {

	private static final Logger LOGGER=Logger.getLogger(DepartmentRating.class);
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LOGGER.debug("DepartmentRating#doGet()");
		
		int departmnetId;
		String id=request.getParameter("id");
		if (id!=null){
			departmnetId=Integer.parseInt(id);
			List<UserTotalMark> depRating= new UserPositionDAO().getUsersForDepartmentRating(departmnetId);
			request.setAttribute("depRating", depRating);
			request.setAttribute("id", departmnetId);
		}
		
		request.getRequestDispatcher(Constants.PAGE_DEPARTMENT_RATING).forward(request, response);
	}

}
