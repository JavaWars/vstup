package com.lazarev.web.servlets.superadmin;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.lazarev.service.PhaseService;
import com.lazarev.web.Constants;

@WebServlet("/phase")
public class PhaseServlet extends HttpServlet {
	
	private static final Logger LOGGER=Logger.getLogger(PhaseServlet.class);
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LOGGER.trace("PhaseServlet.doGet()");
		request.setAttribute("phase", PhaseService.getCurrentPhase().name());
		request.getRequestDispatcher(Constants.PAGE_SUPERADMIN_PHASE).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LOGGER.trace("PhaseServlet.doPost()");

		String phaseName=(String) request.getParameter("phase");
		LOGGER.trace("PhaseName"+phaseName);
		if (phaseName!=null) {
			PhaseService.instance().setPhase(phaseName);
		}
		response.sendRedirect(Constants.COMMAND_HOME);
	}

}
