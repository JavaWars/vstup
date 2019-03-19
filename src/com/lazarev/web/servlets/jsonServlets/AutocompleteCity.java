package com.lazarev.web.servlets.jsonServlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.lazarev.db.dao.CityDAO;
import com.lazarev.web.json.JsonPacker;

@WebServlet("/autocomplete/city")
public class AutocompleteCity extends HttpServlet {

	private static final Logger LOGGER = Logger.getLogger(AutocompleteCity.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LOGGER.debug("Autocomplite#doGet()");
		String term = request.getParameter("term");
		LOGGER.trace(term);
		List<String> cityList = new CityDAO().getAllCitysWithName(term);
		response.getWriter().print(JsonPacker.listToJsonArray(cityList));
	}
}
