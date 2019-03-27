package com.lazarev.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.lazarev.db.dao.QueryDAO;
import com.lazarev.db.entity.extra.UserMarkQuery;
import com.lazarev.web.servlets.helper.ImgConverter;

public class QueryService {
	private static Logger logger = Logger.getLogger(QueryService.class);

	public List<UserMarkQuery> getAllQueriesForDepartment(String department) {
		
		if (department==null) {department="";}
		
		List<UserMarkQuery> result=new QueryDAO().getAllQueriesFromUsersForDepartment(department);
		
		for (UserMarkQuery umq:result) {
			umq.setDiplomImg(ImgConverter.fileNameTo64BaseData(String.valueOf(umq.getUserId())));
		}
		
		return result;
	}

	public void deleteFromUnconfirmedList(int queryId) {
		new QueryDAO().delete(queryId);
	}
}
