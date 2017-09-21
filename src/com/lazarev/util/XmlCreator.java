package com.lazarev.util;

import java.io.File;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.lazarev.db.entity.Department;
import com.lazarev.db.entity.extra.UserTotalMark;

public class XmlCreator {

	/**
	 * Saves Test object to XML file.
	 * 
	 * @param test
	 *            Test object to be saved.
	 * @param xmlFileName
	 *            Output XML file name.
	 */
	public static void saveToXML(Department department, List<UserTotalMark> users, String xmlFileName)
			throws ParserConfigurationException, TransformerException {
		// data -> DOM -> XML
		saveToXML(getDocument(department, users), xmlFileName);
	}

	private static void saveToXML(Document document, String xmlFileName) throws TransformerException {
		StreamResult result = new StreamResult(new File(xmlFileName));

		// set up transformation
		TransformerFactory tf = TransformerFactory.newInstance();
		javax.xml.transform.Transformer t = tf.newTransformer();
		t.setOutputProperty(OutputKeys.INDENT, "yes");

		// run transformation
		t.transform(new DOMSource(document), result);
	}

	private static Document getDocument(Department department, List<UserTotalMark> users)
			throws ParserConfigurationException {
		// obtain DOM parser
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		// set properties for Factory

		// XML document contains namespaces
		dbf.setNamespaceAware(true);

		DocumentBuilder db = dbf.newDocumentBuilder();
		Document document = db.newDocument();

		// create root element
		Element sElement = document.createElement("students");

		// add root element
		document.appendChild(sElement);

		Element dElement = document.createElement("department");
		dElement.setTextContent(department.getName());
		sElement.appendChild(dElement);

		for (UserTotalMark mark : users) {
			Element userMarksElement = document.createElement("student");

			Element uNameElement = document.createElement("name");
			uNameElement.setTextContent(mark.getName());
			userMarksElement.appendChild(uNameElement);

			Element u2NameElement = document.createElement("secondName");
			u2NameElement.setTextContent(mark.getSecondName());
			userMarksElement.appendChild(u2NameElement);

			Element uMarkElement = document.createElement("mark");
			uMarkElement.setTextContent(String.valueOf(mark.getMark()));
			userMarksElement.appendChild(uMarkElement);

			Element uCityElement = document.createElement("city");
			uCityElement.setTextContent(mark.getCityArea());
			userMarksElement.appendChild(uCityElement);

			sElement.appendChild(userMarksElement);
		}

		return document;
	}

}
