package com.lazarev.web.servlets.admin;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.lazarev.service.MarkService;

@WebServlet("/InsertMarkForStudent")
public class InsertMarkForStudent extends HttpServlet {
	
	private static final Logger LOGGER = Logger.getLogger(InsertMarkForStudent.class);
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		LOGGER.trace(request.getParameter("userId")+"|"+request.getParameter("subjectId")+"|"+request.getParameter("mark"));
		int userId,subjectId;
		double mark;
		userId= Integer.parseInt(request.getParameter("userId"));
		subjectId=Integer.parseInt(request.getParameter("subjectId"));
		mark=Double.parseDouble(request.getParameter("mark"));
		LOGGER.trace("InsertMarkForStudent.doPost()"+"userId"+userId+"|subjectId"+subjectId+"|mark"+mark);
		new MarkService().insertMarkForStudent(userId,subjectId,mark);
	}

}
