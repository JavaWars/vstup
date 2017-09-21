package com.lazarev.web.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.apache.log4j.Logger;

public class EncodingFilter implements Filter {

	private static Logger logger = Logger.getLogger(EncodingFilter.class);

	private String encoding;

	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
//		logger.debug("encodding>");
		
		if (null == request.getCharacterEncoding()) {
			request.setCharacterEncoding(encoding);
			logger.trace("set encoding "+encoding);
		}

		// Set the default response content type and encoding
		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		chain.doFilter(request, response);
		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
//		logger.debug("encodding<");
	
	}

	public void init(FilterConfig fConfig) throws ServletException {
		encoding = fConfig.getInitParameter("encoding");
		logger.debug("EncoddingFilter initialization complite with " + encoding.toUpperCase() + " parameters");
	}

}
