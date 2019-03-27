package com.lazarev.web.servlets.admin;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.lazarev.db.dao.UserDAO;
import com.lazarev.db.entity.User;
import com.lazarev.db.entity.extra.UserWithMarks;
import com.lazarev.service.MarkService;
import com.lazarev.service.UserService;
import com.lazarev.web.Constants;

@WebServlet("/cleanUser")
public class GetUsersClean extends HttpServlet {

	private static final Logger LOGGER = Logger.getLogger(GetUsersClean.class);

	// return pages with not banned users
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LOGGER.debug("GetUsersClean()@doGet()");

		String fio="",email = "",diplom="";
		int page=1;
		if (request.getParameter("fio")!=null) {fio=(String)request.getParameter("fio");}
		if (request.getParameter("email")!=null) {email=(String)request.getParameter("email");}
		if (request.getParameter("diplom")!=null) {diplom=(String)request.getParameter("diplom");}
		if (request.getParameter("page")!=null) {page= Integer.parseInt(request.getParameter("page"));}
		
		List<UserWithMarks> users = new UserService().getNotBunnedByExample(fio,email,diplom,page);
		System.out.println(users);
		request.setAttribute("isPageForBlocking", true);
		request.setAttribute("users", users);
		
		request.setAttribute("page", page);
		request.setAttribute("filterFio", fio);
		request.setAttribute("filterDiplom", diplom);
		request.setAttribute("filterEmail", email);
		request.setAttribute("universityMarks",new MarkService().getAllMarksSettedByAdmin());
		
		request.getRequestDispatcher(Constants.PAGE_ADMIN_ALL_USERS).forward(request, response);
	}

}
