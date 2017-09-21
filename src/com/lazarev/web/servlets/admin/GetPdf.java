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

import com.lazarev.util.PdfCreator;
import com.lazarev.util.PdfCreator;
import com.lazarev.web.Constants;

@WebServlet("/pdf")
public class GetPdf extends HttpServlet {

	private static final Logger LOGGER = Logger.getLogger(GetPdf.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LOGGER.debug("GetPdf#doGet()");

		String depId = request.getParameter("id");
		if (depId != null) {
			int id = Integer.parseInt(depId);
			String path = Constants.PDF_HOME + id + ".pdf";
			LOGGER.trace("returned pdf from path: " + path);

			//
			String xslPath = getServletContext().getRealPath(Constants.PDF_TEMPLATE_DIR);
			LOGGER.info("servlet context path" + xslPath);
			PdfCreator.create(xslPath, id);

			returnPdf(response, path);
		}
	}

	private void returnPdf(HttpServletResponse response, String path) throws IOException {

		File pdfFile = new File(path);

		// for downloading
		// response.addHeader("Content-Disposition", "attachment;
		// filename=abc");

		response.setContentType("application/pdf");

		response.setContentLength((int) pdfFile.length());

		FileInputStream fileInputStream = new FileInputStream(pdfFile);
		OutputStream responseOutputStream = response.getOutputStream();

		int bytes;
		while ((bytes = fileInputStream.read()) != -1) {
			responseOutputStream.write(bytes);
		}

	}

}
