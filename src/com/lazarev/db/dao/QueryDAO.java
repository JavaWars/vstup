package com.lazarev.db.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.lazarev.db.entity.extra.UserMarkQuery;
import com.lazarev.exception.MyAppException;

public class QueryDAO extends DAO<Object, Integer> {

	private static final Logger LOGGER = Logger.getLogger(QueryDAO.class);
	private static final String SELECT_ALL_NEW_QUERIES = "call getWaitListForDepartment(?)";
	private static final String DELETE_BY_ID = "call delete_from_query(?)";

	public List<UserMarkQuery> getAllQueriesFromUsersForDepartment(String department) {
		LOGGER.trace("QueryDAO.getAllQueriesFromUsersForDepartment()");
		List<UserMarkQuery> result = new LinkedList();

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = getPreparedStatement(null, SELECT_ALL_NEW_QUERIES);

			preparedStatement.setString(1, department);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				result.add(prepareUserMarkQuery(resultSet));
			}

		} catch (SQLException e) {
			LOGGER.error("can not get Departments  with name", e);
			throw new MyAppException("something going wrong with db", e);
		} finally {
			close(preparedStatement);
			close(resultSet);
			close(connection);
		}

		return result;
	}

	private UserMarkQuery prepareUserMarkQuery(ResultSet resultSet) {
		UserMarkQuery umq = new UserMarkQuery();

		try {
			umq.setFio(resultSet.getString("fio"));
			umq.setDiplom(resultSet.getString("diplom"));
			umq.setQueryId(resultSet.getInt("query_id"));
			umq.setUserId(resultSet.getInt("userId"));
			umq.setMaxPosibleMark(resultSet.getDouble("max_posible_value"));
			umq.setMark(resultSet.getDouble("mark"));
			umq.setMarkOriginalName(resultSet.getString("name"));
		} catch (SQLException e) {
			LOGGER.error("cant prepare UserMarkQuery");
			e.printStackTrace();
		}

		return umq;
	}

	@Override
	public Object get(Integer key) {
		return null;
	}

	@Override
	public List<Object> getAll() {
		return null;
	}

	@Override
	public boolean delete(Integer id) {
		boolean deleted=false;
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = getPreparedStatement(null, DELETE_BY_ID);

			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
			deleted=true;
		} catch (SQLException e) {
			LOGGER.error("can not delete query with id"+id, e);
			throw new MyAppException("can not delete query with id"+id, e);
		} finally {
			close(preparedStatement);
			close(connection);
		}

		return deleted;
	}

	@Override
	public boolean update(Object entity) {
		return false;
	}

	@Override
	public boolean insert(Object entity) {
		return false;
	}

}
