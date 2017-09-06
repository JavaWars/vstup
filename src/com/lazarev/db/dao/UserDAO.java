package com.lazarev.db.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.lazarev.db.MySqlConnection.MyTomcatPool;
import com.lazarev.db.entity.User;

public class UserDAO extends DAO<User, Integer> {

	private static Logger logger = Logger.getLogger(UserDAO.class);

	private static final String SELECT_ALL_USERS = "SELECT users.id, users.`name`, users.secondName, users.email, users.`password`, users.id_role, users.id_city, users.area"
			+ " FROM users INNER JOIN roles ON users.id_role = roles.id where roles.roleName='USER'";

	private static final String SELECT_USER_BY_LOGIN_AND_PASSWORD = "Select * from users where email=? and password=? ";

	private static final String INSERT_INTO_USER = "insert into users values( DEFAULT ,?, ?, ?, ?, ?, ?, ?);";

	private static final String SELECT_ALL_BANNED_USERS = "SELECT users.id, users.`name`, users.secondName, users.email, users.`password`, users.id_role, users.id_city, users.area"
			+ " FROM ban INNER JOIN users ON ban.userId = users.id";

	private static final String INSERT_BAN_USER = "insert into ban values(?)";

	private static final String DELETE_BAN_USER = "delete from ban where userId=?";

	private static final String SELECT_ALL_USERS_NOT_BANNED = "select users.* from users, roles"
			+ " where (not users.id=any (select userId from ban)) and (roles.id=users.id_role) and (roles.roleName='USER')";

	@Override
	public User get(Integer key) {
		return null;
	}

	// only user, not admin
	@Override
	public List<User> getAll() {
		logger.trace("UserDAO#getAllUsers()");
		List<User> result = new LinkedList<>();

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = getPreparedStatement(null, SELECT_ALL_USERS);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				result.add(prepareUser(resultSet));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(preparedStatement);
			close(resultSet);
			close(connection);
		}
		return result;
	}

	@Override
	public boolean delete(Integer id) {
		return false;
	}

	@Override
	public boolean update(User entity) {
		return false;
	}

	@Override
	public boolean insert(User entity) {

		boolean inserted = false;

		PreparedStatement prepared = getPreparedStatement(connection, INSERT_INTO_USER);
		logger.trace("inserted user" + entity);
		try {
			int k = 1;
			prepared.setString(k++, entity.getName());
			prepared.setString(k++, entity.getSecondName());
			prepared.setString(k++, entity.getEmail());
			prepared.setString(k++, entity.getPassword());

			prepared.setInt(k++, entity.getRoleId());
			prepared.setInt(k++, entity.getCityId());

			prepared.setString(k++, entity.getCityArea());

			logger.debug("insert user" + prepared.executeUpdate());
			inserted = true;
		} catch (SQLException e) {
			logger.error("cannot insert new user", e);
		} finally {
			close(prepared);
			close(connection);
		}
		return inserted;
	}

	///////////////////////////////////////
	// other operation

	public boolean ckeck(User user) {

		logger.debug("checking User");
		connection = MyTomcatPool.getInstance().getConnection();

		PreparedStatement prepared = getPreparedStatement(connection, SELECT_USER_BY_LOGIN_AND_PASSWORD);

		ResultSet set = null;

		try {
			int k = 1;
			prepared.setString(k++, user.getEmail());
			prepared.setString(k++, user.getPassword());

			set = prepared.executeQuery();

			if (set.next()) {
				updateUserData(user, prepareUser(set));
				return true;
			}

		} catch (SQLException e) {

			logger.error("cant get result from table user by query " + SELECT_USER_BY_LOGIN_AND_PASSWORD, e);

		} finally {
			close(prepared);
			close(set);
			close(connection);
		}
		return false;
	}

	///////////////////////////////////////
	// preparators
	private User prepareUser(ResultSet resultSet) {
		User user = new User();

		try {
			user.setId(resultSet.getInt("id"));
			user.setEmail(resultSet.getString("email"));
			user.setPassword(resultSet.getString("password"));
			user.setName(resultSet.getString("name"));
			user.setSecondName(resultSet.getString("secondName"));
			user.setCityArea(resultSet.getString("area"));
			user.setCityId(resultSet.getInt("id_city"));
			user.setRoleId(resultSet.getInt("id_role"));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return user;
	}

	private void updateUserData(User user, User data) {
		user.setId(data.getId());
		user.setEmail(data.getEmail());
		user.setPassword(data.getPassword());
		user.setName(data.getName());
		user.setSecondName(data.getSecondName());
		user.setCityArea(data.getCityArea());
		user.setCityId(data.getCityId());
		user.setRoleId(data.getRoleId());
	}

	public List<User> getAllBanned() {
		logger.trace("UserDAO#getAllBannedUsers()");
		List<User> result = new LinkedList<>();

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = getPreparedStatement(null, SELECT_ALL_BANNED_USERS);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				result.add(prepareUser(resultSet));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(preparedStatement);
			close(resultSet);
			close(connection);
		}
		return result;
	}

	public void banUser(int userId) {
		logger.debug("block user" + userId);

		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = getPreparedStatement(null, INSERT_BAN_USER);
			preparedStatement.setInt(1, userId);
			int x = preparedStatement.executeUpdate();

			if (x == 1) {
				logger.trace("user" + userId + " blocked");
			} else {
				logger.error("user not banned");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(preparedStatement);
			close(connection);
		}

	}

	public void unBlockUser(int userId) {
		logger.debug("unblock user" + userId);
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = getPreparedStatement(null, DELETE_BAN_USER);
			preparedStatement.setInt(1, userId);
			int x = preparedStatement.executeUpdate();

			if (x == 1) {
				logger.trace("user" + userId + " unBlocked");
			} else {
				logger.error("user not unBlocked");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(preparedStatement);

			close(connection);
		}
	}

	public List<User> getAllNotBanned() {
		logger.trace("UserDAO#getAllUsers()");
		List<User> result = new LinkedList<>();

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = getPreparedStatement(null, SELECT_ALL_USERS_NOT_BANNED);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				result.add(prepareUser(resultSet));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(preparedStatement);
			close(resultSet);
			close(connection);
		}
		return result;
	}
}
