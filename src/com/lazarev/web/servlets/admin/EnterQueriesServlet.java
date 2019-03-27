package com.lazarev.web.servlets.admin;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.lazarev.service.DepartmentService;
import com.lazarev.service.QueryService;
import com.lazarev.web.Constants;

@WebServlet("/enterQueries")
public class EnterQueriesServlet extends HttpServlet {

	private static final Logger LOGGER=Logger.getLogger(EnterQueriesServlet.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LOGGER.trace("enterQueries.doGet()");
		
		String department=request.getParameter("dep");
		
		request.setAttribute("users", new QueryService().getAllQueriesForDepartment(department));
		request.setAttribute("departments", new DepartmentService().getAllDep());
		
		request.getRequestDispatcher(Constants.PAGE_ADMIN_ENTER_QUERIES).forward(request, response);
	}

//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		
//	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		LOGGER.trace("enterQueries.doDelete()"+req.getParameter("queriesId")+req.getAttribute("queriesId"));
		int queryId=Integer.parseInt(req.getParameter("queriesId"));
		new QueryService().deleteFromUnconfirmedList(queryId);
	}
}
