package com.lazarev.db.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.lazarev.db.Role;

public class RoleDAO extends DAO<Role, Integer> {

	private static Logger logger = Logger.getLogger(RoleDAO.class);

	private static final String GET_ROLE_BY_USER_ID = "SELECT roles.roleName FROM roles"
			+ " INNER JOIN users ON users.id_role = roles.id where users.id=?";

	private static final String GET_ROLE_BY_ID = "Select roles.roleName FROM roles where roles.id=?";

	private static final String GET_ROLE_ID_BY_ROLE_NAME = "Select roles.id from roles where roles.roleName=?";

	@Override
	public Role get(Integer key) {
		Role role = null;

		ResultSet result = null;

		PreparedStatement prepared = getPreparedStatement(null, GET_ROLE_BY_ID);
		try {
			logger.info(key);
			prepared.setInt(1, key);

			result = prepared.executeQuery();

			if (result.next()) {
				role = prepareRole(result);
			}
		} catch (SQLException e) {

			logger.error("cant get role using QUERY: " + GET_ROLE_BY_USER_ID, e);

		} finally {
			close(prepared);
			close(result);
			close(connection);
		}
		return role;
	}

	@Override
	public List<Role> getAll() {
		return null;
	}

	@Override
	public boolean delete(Integer id) {
		return false;
	}

	@Override
	public boolean update(Role entity) {
		return false;
	}

	@Override
	public boolean insert(Role entity) {
		return false;
	}

	/////////////////////////////////////////
	// other method
	public Role getByUserKey(int roleId) {
		Role role = null;

		ResultSet result = null;

		PreparedStatement prepared = getPreparedStatement(null, GET_ROLE_BY_USER_ID);
		try {
			prepared.setInt(1, roleId);

			result = prepared.executeQuery();

			if (result.next()) {
				role = prepareRole(result);
			}
		} catch (SQLException e) {

			logger.error("cant get role using QUERY: " + GET_ROLE_BY_USER_ID, e);

		} finally {
			close(prepared);
			close(result);
			close(connection);
		}
		return role;
	}

	public Integer getRoleIdByRoleName(String roleName) {
		Integer result = null;

		ResultSet resultSet = null;

		PreparedStatement prepared = getPreparedStatement(null, GET_ROLE_ID_BY_ROLE_NAME);
		try {
			prepared.setString(1, roleName);

			resultSet = prepared.executeQuery();

			if (resultSet.next()) {
				result = resultSet.getInt(1);
			}
		} catch (SQLException e) {

			logger.error("cant get role using QUERY: " + GET_ROLE_ID_BY_ROLE_NAME, e);

		} finally {
			close(prepared);
			close(resultSet);
			close(connection);
		}

		return result;
	}

	///////////////////////////////////////////
	// prepare Role
	private Role prepareRole(ResultSet result) {
		Role role = null;

		try {
			role = Role.valueOf(result.getString(1));
		} catch (SQLException e) {
			logger.error("cant prepare Role", e);
		}

		return role;
	}
}
