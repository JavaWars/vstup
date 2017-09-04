package com.lazarev.web.servlets.operations;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.lazarev.db.Role;
import com.lazarev.web.Constants;

public class Operations {

	private static Logger logger = Logger.getLogger(Operations.class);

	public static List<PossibleOperations> getAvalibleOperations(Role role, String contextPath) {

		List<PossibleOperations> result = new LinkedList<>();
		result.add(new PossibleOperations("Home", contextPath + Constants.PAGE_HOME));
		result.add(new PossibleOperations("Logout", contextPath + Constants.PAGE_LOGOUT));

		if (role != null) {
			switch (role) {
			case ADMIN:
				result.add(new PossibleOperations("Admin Operations", contextPath + Constants.PAGE_HOME));

				break;
			case USER:
				result.add(new PossibleOperations("User Operations", contextPath + Constants.PAGE_HOME));

				break;
			case SUPERADMIN:

				break;

			}
		}
		logger.debug("operations for " + Arrays.toString(result.toArray()));
		return result;
	}

}
