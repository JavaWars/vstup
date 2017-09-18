package com.lazarev.web.servlets.admin;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.lazarev.web.Constants;

@WebServlet("/pdf")
public class GetPdf extends HttpServlet {

	private static final Logger LOGGER = Logger.getLogger(GetPdf.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LOGGER.debug("GetPdf#doGet()");
		// this.getServletContext().getRealPath(File.separator)
		String path = Constants.PDF_HOME + "d.pdf";
		LOGGER.trace(path);

		int BUFF_SIZE = 1024;
		byte[] buffer = new byte[BUFF_SIZE];
		response.setContentType("application/pdf");
		response.setHeader("Content-Type", "application/pdf");
		File filePDF = new File(path);
		FileInputStream fis = new FileInputStream(filePDF);
		OutputStream os = response.getOutputStream();
		try {
			response.setContentLength((int) filePDF.length());
			int byteRead = 0;
			while ((byteRead = fis.read()) != -1) {
				os.write(byteRead);
			}
			os.flush();
		} catch (Exception excp) {
			excp.printStackTrace();
		} finally {
			os.close();
			fis.close();
		}

	}

}
