package com.lazarev.web.filters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.lazarev.db.Role;
import com.lazarev.web.Constants;

public class AccessFilter implements Filter {

	private Logger logger = Logger.getLogger(AccessFilter.class);

	private Map<Role, List<String>> accessMap = new HashMap<Role, List<String>>();

	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		logger.debug("filter>");

		if (!goIfAccessIsFree(request, response, chain)) {

			logger.debug("not a freedom");

			// step 1 role exist? (no==> login)
			Role role = getUserRoleIfExist(request);

			if (role != null) {
				logger.debug("ROLE in Session " + role);

				// need to check access from settings
				if (!isAccess(request, role)) {

					String path = Constants.PAGE_ACCESS_ERROR;

					logger.info("NO ACCESS, redirecting to " + path);

					request.getRequestDispatcher(path).forward(request, response);

				} else {

					String path = ((HttpServletRequest) request).getServletPath();

					logger.debug("User have access to seccurity Zone redddirecting to path " + path);

				}
			} else {

				String path = ((HttpServletRequest) request).getContextPath() + Constants.PAGE_LOGIN;

				logger.info("NO ACCESS, redirecting to " + path);

				((HttpServletResponse) response).sendRedirect(path);
			}

		}
		chain.doFilter(request, response);
logger.debug("filter<");
	}

	private boolean goIfAccessIsFree(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		// ((HttpServletRequest) request).getContextPath()
		
		String path = ((HttpServletRequest) request).getServletPath();
		if (!needAsses(path)) {
			logger.debug("free for all users path " + path);
			return true;
		} else {
			logger.debug("not for all users path " + path);
		}
		return false;
	}

	private Role getUserRoleIfExist(ServletRequest request) {
		Role role = null;
		HttpServletRequest httpRequest = (HttpServletRequest) request;

		HttpSession session = httpRequest.getSession(false);
		if (session == null) {

			logger.info("cant get Role for user session is null");

		} else {

			role = Role.valueOf((String) session.getAttribute("ROLE"));

			if (role == null) {
				logger.info("USER ROLE is null");
			}
		}
		return role;
	}

	private boolean isAccess(ServletRequest request, Role userRole) {

		HttpServletRequest httpRequest = (HttpServletRequest) request;

		// what user want to get
		String path = httpRequest.getServletPath();
		logger.trace("PATH: " + path);

		// if no check Role (using table)
		boolean result = accessMap.get(userRole).contains(path);
		if (result) {
			logger.trace("ACCESS ALLOWED PATH: " + path + " USER ROLE: " + userRole.getName());
		} else {
			logger.trace("ACCESS DENIED PATH: " + path + " USER ROLE: " + userRole.getName());
		}
		return result;
	}

	// return true if user need any access rule
	private boolean needAsses(String path) {

		List<String> allSpecialPages = new LinkedList<>();

		for (Entry<Role, List<String>> entry : accessMap.entrySet()) {
			allSpecialPages.addAll(entry.getValue());
		}

		return allSpecialPages.contains(path);

	}

	public void init(FilterConfig fConfig) throws ServletException {
		logger.debug("AccessFilter init");

		putAndPrint(fConfig, Role.SUPERADMIN);
		putAndPrint(fConfig, Role.ADMIN);
		putAndPrint(fConfig, Role.USER);
		// putAndPrint(fConfig, Role.COMMON);

	}

	private void putAndPrint(FilterConfig fConfig, Role role) {

		List<String> container = asList(fConfig.getInitParameter(role.getName()));

		accessMap.put(role, container);

		logger.info(role.toString() + " operations" + Arrays.toString(container.toArray()));

	}

	///////////////////////////////////////////////
	// util
	private List<String> asList(String str) {
		List<String> list = new ArrayList<String>();
		StringTokenizer st = new StringTokenizer(str);
		while (st.hasMoreTokens()) {
			list.add(st.nextToken());
		}
		return list;
	}

}
