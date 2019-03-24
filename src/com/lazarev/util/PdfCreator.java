package com.lazarev.util;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import com.lazarev.db.dao.DepartmentDAO;
import com.lazarev.db.dao.UserPositionDAO;
import com.lazarev.db.entity.Department;
import com.lazarev.db.entity.extra.UserTotalMark;
import com.lazarev.web.Constants;

public class PdfCreator {

	private static final Logger LOGGER = Logger.getLogger(PdfCreator.class);

	public static void create(String settingPath, int depId) {
		LOGGER.debug("PREPARING AND CREATING PDF CALLED");

		
		// step 0 prepare data for document
		Department department = new DepartmentDAO().get(depId);
		List<UserTotalMark> users = new UserPositionDAO().getUsersForDepartmentRating(depId);

		// step 1 create xml
		String fileName = Constants.OUTPUT_DIR + String.valueOf(depId) + ".xml";
		try {
			XmlCreator.saveToXML(department, users, fileName);
		} catch (ParserConfigurationException | TransformerException e) {
			LOGGER.error("can't create xml file", e);
			e.printStackTrace();
		}

		// step 2 create pdf from xml data
		PdfCreator fOPPdfDemo = new PdfCreator();
		
			try {
				fOPPdfDemo.convertToPDF(settingPath, fileName, String.valueOf(depId) +".pdf");
			} catch (IOException | TransformerException | SAXException e) {
				LOGGER.error("can't create pdf file", e);
				e.printStackTrace();
			}
	}

	/**
	 * Method that will convert the given XML to PDF
	 * 
	 * @param it's
	 *            contend dir
	 * @throws IOException
	 * @throws TransformerException
	 * @throws SAXException 
	 */
	private void convertToPDF(String settingPath, String xmlDataForCreatingPdf, String resultFileName)
			throws IOException, TransformerException, SAXException {
		// the XSL FO file
		File xsltFile = new File(settingPath + "//template.xsl");
		// the XML file which provides the input
		StreamSource xmlSource = new StreamSource(new File(xmlDataForCreatingPdf));
		// create an instance of fop factory

		File userConfig = new File(settingPath+"fopConfig.xml");//
		FopFactory fopFactory = FopFactory.newInstance(userConfig);//new File(".").toURI()
		// a user agent is needed for transformation
		FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
		// Setup output
		OutputStream out;
		out = new java.io.FileOutputStream(Constants.OUTPUT_DIR + resultFileName);

		try {
			// Construct fop with desired output format
			Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);

			// Setup XSLT
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer(new StreamSource(xsltFile));

			// Resulting SAX events (the generated FO) must be piped through to
			// FOP
			Result res = new SAXResult(fop.getDefaultHandler());

			// Start XSLT transformation and FOP processing
			// That's where the XML is first transformed to XSL-FO and then
			// PDF is created
			transformer.transform(xmlSource, res);
		} finally {
			out.close();
		}
	}

}
