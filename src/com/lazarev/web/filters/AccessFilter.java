package com.lazarev.web.filters;

import java.io.IOException;
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

import com.lazarev.db.entity.Role;
import com.lazarev.db.dao.UserDAO;
import com.lazarev.web.Constants;

public class AccessFilter implements Filter {

	private static Logger logger = Logger.getLogger(AccessFilter.class);

	private Map<Role, List<String>> accessMap = new HashMap<Role, List<String>>();

	private List<String> allSpecialPages = new LinkedList<>();
	
	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		logger.debug("filter>");

		// user email
		String userLogin = (String) ((HttpServletRequest) request).getSession().getAttribute("EMAIL");
		// checking is user banned by admin
		boolean banned = new UserDAO().isBlocked(userLogin);
		if (banned) {
			logger.info("banned user detected");
			((HttpServletRequest) request).getSession().invalidate();
			((HttpServletRequest) request).setAttribute("errorMessage", "You are banned on this site");
			((HttpServletRequest) request).getRequestDispatcher(Constants.PAGE_ERROR).forward(request, response);
		} else {
			if (!goIfAccessIsFree(request, response, chain)) {

				logger.debug("not a freedom");

				// step 1 role exist? (no==> login)
				Role role = getUserRoleIfExist(request);

				if (role != null) {
					logger.debug("ROLE in Session " + role);

					// need to check access from settings for current user
					if ((!isAccess(request, role))) {

						String path = ((HttpServletRequest) request).getContextPath() + Constants.PAGE_ACCESS_ERROR;
						logger.info("NO ACCESS, redirecting to " + path);

						((HttpServletResponse) response).sendRedirect(path);

					} else {

						logger.debug("User have access to seccurity Zone reddirecting to path "
								+ ((HttpServletRequest) request).getServletPath());

						chain.doFilter(request, response);

					}
				} else {

					String path = Constants.COMMAND_LOGIN;
					logger.info("NO ACCESS, redirecting to " + path);
					((HttpServletResponse) response).sendRedirect(path);

				}

			} else {
				// access is free for this page
				chain.doFilter(request, response);
			}
		}

		logger.debug("filter<");
	}

	private boolean goIfAccessIsFree(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

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

			Object objRole = session.getAttribute("ROLE");
			if (objRole != null) {
				role = Role.valueOf((String) objRole);
				logger.info("USER ROLE is not null");

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

		return allSpecialPages.contains(path);

	}

	public void init(FilterConfig fConfig) throws ServletException {
		logger.debug("AccessFilter init");
		putAndPrint(fConfig, Role.SUPERADMIN);
		putAndPrint(fConfig, Role.ADMIN);
		putAndPrint(fConfig, Role.USER);
		for (Entry<Role, List<String>> entry : accessMap.entrySet()) {
			allSpecialPages.addAll(entry.getValue());
		}
	}

	private void putAndPrint(FilterConfig fConfig, Role role) {

		List<String> container = asList(fConfig.getInitParameter(role.getName()));

		accessMap.put(role, container);

		logger.info(role.toString() + " operations" + Arrays.toString(container.toArray()));

	}

	///////////////////////////////////////////////
	// util
	private List<String> asList(String str) {
		List<String> list = new LinkedList<String>();
		StringTokenizer st = new StringTokenizer(str);
		while (st.hasMoreTokens()) {
			list.add(st.nextToken());
		}
		return list;
	}
}
