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
//		result.add(new PossibleOperations("Home", contextPath + Constants.PAGE_HOME));

		if (role != null) {
			switch (role) {
			case ADMIN:
				result.add(new PossibleOperations("Departments", contextPath + Constants.COMMAND_ALL_DEPARTMENTS));
				result.add(new PossibleOperations("New Department", contextPath + Constants.COMMAND_ADMIN_CREATE_NEW_DEPARTMENT));
				result.add(new PossibleOperations("Statistic", contextPath + Constants.COMMAND_ADMIN_STATISTIC));
				result.add(new PossibleOperations("Users", contextPath + Constants.COMMAND_ADMIN_GET_USERS));
				result.add(new PossibleOperations("Blocked users", contextPath + Constants.COMMAND_ADMIN_GET_BLOCKED_USERS));

				break;
			case USER:
				
				result.add(new PossibleOperations("Profile", contextPath + Constants.COMMAND_USER_EDIT_PROFILE));
				result.add(new PossibleOperations("Departments", contextPath + Constants.COMMAND_ALL_DEPARTMENTS));
				result.add(new PossibleOperations("My statistic", contextPath + Constants.COMMAND_USER_STATISTIC));

				break;
			default:
				logger.error("NOT A USER");
				break;
			}
		}
		result.add(new PossibleOperations("Logout", contextPath + Constants.COMMAND_LOGOUT));

		logger.debug("operations for " + Arrays.toString(result.toArray()));
		return result;
	}

}
