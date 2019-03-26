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
import com.lazarev.exception.MyAppException;
import com.lazarev.service.MarkService;
import com.lazarev.service.UserService;
import com.lazarev.web.Constants;
import com.lazarev.web.json.JsonExtracter;
import com.lazarev.web.servlets.helper.Helper;
import com.lazarev.web.servlets.helper.ImgConverter;

@WebServlet("/userMark")
public class UserMark extends HttpServlet {

	private static final Logger LOGGER = Logger.getLogger(UserMark.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LOGGER.debug("UserMark#doGet()");

		String email = (String) request.getSession().getAttribute("EMAIL");
		if (email != null) {
			int id = new UserDAO().getIdByEmail(email);
			List<StudentMark> subjects = new MarkDAO().getAll(id);
			request.setAttribute("subjects", subjects);
			request.setAttribute("file", ImgConverter.fileNameTo64BaseData(String.valueOf(id)));
			request.setAttribute("profile", new UserService().getUserById(id));
			request.getRequestDispatcher(Constants.PAGE_USER_PROFILE).forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LOGGER.debug("UserMark#doPost()");

		Department department = new Department();
		List<StudentMark> marks = new LinkedList<>();
		JsonExtracter.extractUserMarks(Helper.getJsonQuery(request), department, marks);
		String userLogin = (String) request.getSession().getAttribute("EMAIL");

		try {
			new MarkService().insertUserMark(userLogin, department, marks);
		}
		catch (MyAppException e) {
			LOGGER.error(e.getMessage());
			response.getWriter().append(e.getMessage());
			response.setStatus(500);
		}
	}
	
//	@Override
//	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//	
//		req.getAttribute("fio");
//		req.getAttribute("diplom");
//	}

}
