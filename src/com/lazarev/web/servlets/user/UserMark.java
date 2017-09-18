package com.lazarev.web.servlets.user;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.lazarev.db.dao.MarkDAO;
import com.lazarev.db.dao.UserDAO;
import com.lazarev.db.entity.Department;
import com.lazarev.db.entity.StudentMark;
import com.lazarev.web.Constants;
import com.lazarev.web.json.JsonExtracter;
import com.lazarev.web.servlets.helper.Helper;

@WebServlet("/userMark")
public class UserMark extends HttpServlet {
	
	private static final Logger LOGGER=Logger.getLogger(UserMark.class);
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LOGGER.debug("UserMark#doGet()");
		
		String email=(String) request.getSession().getAttribute("EMAIL");
		if (email!=null){
			int id=new UserDAO().getIdByEmail(email);
			List<StudentMark> subjects=new MarkDAO().getAll(id);
			request.setAttribute("subjects", subjects);
			request.getRequestDispatcher(Constants.PAGE_USER_PROFILE).forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LOGGER.debug("UserMark#doPost()");
		
		Department department = new Department();
		List<StudentMark> marks = new LinkedList<>();
		JsonExtracter.extractUserMarks(Helper.getJsonQuery(request), department, marks);
		String userLogin=(String) request.getSession().getAttribute("EMAIL");
		new MarkDAO().insertUserMarks(department, marks,userLogin);
	}

}
