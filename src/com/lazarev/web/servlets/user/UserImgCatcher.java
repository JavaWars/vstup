package com.lazarev.web.servlets.user;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.log4j.Logger;

import com.lazarev.db.dao.UserDAO;
import com.lazarev.db.entity.Phase;
import com.lazarev.service.PhaseService;
import com.lazarev.service.UserService;
import com.lazarev.web.Constants;

@WebServlet("/userImgCatcher")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
public class UserImgCatcher extends HttpServlet {

	private static final Logger LOGGER = Logger.getLogger(UserImgCatcher.class);

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LOGGER.debug("UserImgCatcher#doPost()");

		String email = (String) request.getSession().getAttribute("EMAIL");
		if (PhaseService.getCurrentPhase() != Phase.DOCUMENT_SERVE) {
			response.getWriter().append("Wrong phase");
			response.setStatus(500);
		} else {
			
			if (email != null) {
				
				
				int id = new UserDAO().getIdByEmail(email);

				Part filePart = request.getPart("file"); // Retrieves <input
															// type="file"
															// name="file">
				InputStream fileContent = filePart.getInputStream();

				byte[] buffer = new byte[fileContent.available()];
				fileContent.read(buffer);

				File targetFile = new File(Constants.IMG_HOME + String.valueOf(id));
				OutputStream outStream = new FileOutputStream(targetFile);
				outStream.write(buffer);
				outStream.flush();
				outStream.close();
				new UserService().invalidateUser(id);
			}
			response.sendRedirect(Constants.COMMAND_HOME);
		}
	}
}