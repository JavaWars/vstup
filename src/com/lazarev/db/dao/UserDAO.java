package com.lazarev.db.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.lazarev.db.entity.StudentMark;
import com.lazarev.db.entity.User;
import com.lazarev.exception.MyAppException;

public class UserDAO extends DAO<User, Integer> {

	private static Logger logger = Logger.getLogger(UserDAO.class);

	private static final String SELECT_ALL_USERS = "SELECT users.id, users.`name`, users.secondName, users.email, users.`password`, users.id_role, users.id_city, users.area"
			+ " FROM users INNER JOIN roles ON users.id_role = roles.id where roles.roleName='USER'";

	private static final String SELECT_USER_BY_LOGIN_AND_PASSWORD = "Select * from users where email=? and password=? ";

	private static final String INSERT_INTO_USER = "insert into users values( DEFAULT ,?,?, ?, ?, ?, ?, ?,?,?, ?);";

	private static final String SELECT_ALL_BANNED_USERS = "SELECT users.*"
			+ " FROM ban INNER JOIN users ON ban.userId = users.id and "
			+ "users.fio like ? and users.email like ? and users.diplom like ?\r\n"
			+"limit ?,?";

	private static final String INSERT_BAN_USER = "insert into ban values(?)";

	private static final String DELETE_BAN_USER = "delete from ban where userId=?";

	private static final String SELECT_ALL_USERS_NOT_BANNED = "select users.* from users, roles"
			+ " where (not users.id=any (select userId from ban)) and (roles.id=users.id_role) and (roles.roleName='USER') and "
			+ "users.fio like ? and users.email like ? and users.diplom like ?\r\n"
			+"limit ?,?";

	private static final String SELECT_IS_BLOCKED = "SELECT users.* FROM ban INNER JOIN users ON ban.userId = users.id where users.email=?";

	private static final String SELECT_USER_BY_EMAIL = "Select * from users where email=? ";

	private static final String SELECT_COUNT_FOR_USER = "SELECT COUNT(1) FROM student_department where id_student=?";

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
			logger.error("can't get all users", e);
			throw new MyAppException("something going wrong with db", e);
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
		//todo update fio and diplom

		boolean inserted = false;

		PreparedStatement prepared = getPreparedStatement(connection, INSERT_INTO_USER);
		logger.trace("inserted user" + entity);
		try {
			int k = 1;
			prepared.setString(k++, entity.getFio());
			prepared.setString(k++, entity.getName());
			prepared.setString(k++, entity.getSecondName());
			prepared.setString(k++, entity.getEmail());
			prepared.setString(k++, entity.getPassword());

			prepared.setInt(k++, entity.getRoleId());
			prepared.setInt(k++, entity.getCityId());

			prepared.setString(k++, entity.getCityArea());
			
			prepared.setString(k++, entity.getDiplom());
			prepared.setDate(k++, entity.getBirthday());
			logger.debug("insert user" + prepared.executeUpdate());
			inserted = true;
		} catch (SQLException e) {
			logger.error("cannot insert new user", e);
			throw new MyAppException("something going wrong with db", e);
		} finally {
			close(prepared);
			close(connection);
		}
		return inserted;
	}

	///////////////////////////////////////
	// other operation

	public int getIdByEmail(String login) {
		logger.debug("select id by email" + login);

		int result = 0;

		prepareConnectionToWork(connection);
		PreparedStatement prepared = getPreparedStatement(connection, SELECT_USER_BY_EMAIL);
		ResultSet set = null;

		try {
			int k = 1;
			prepared.setString(k++, login);

			set = prepared.executeQuery();

			if (set.next()) {
				result = set.getInt("id");
				logger.info(result);
			}

		} catch (SQLException e) {
			logger.error("can't get result from table user by query " + SELECT_USER_BY_EMAIL, e);
			throw new MyAppException("Something going wrong");
		} finally {
			close(prepared);
			close(set);
		}
		return result;
	}

	public boolean ckeck(User user) {

		logger.debug("checking User");
		prepareConnectionToWork(connection);
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
			throw new MyAppException("something going wrong with db", e);
		} finally {
			close(prepared);
			close(set);
			close(connection);
		}
		return false;
	}

	public List<User> getAllBanned(String fio, String email, String diplom, int start, int fin) {
		logger.trace("UserDAO#getAllBannedUsers()");
		List<User> result = new LinkedList<>();

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			int k=1;
			preparedStatement = getPreparedStatement(null, SELECT_ALL_BANNED_USERS);
			preparedStatement.setString(k++, '%'+fio+'%');
			preparedStatement.setString(k++, '%'+email+'%');
			preparedStatement.setString(k++, '%'+diplom+'%');
			preparedStatement.setInt(k++, start);
			preparedStatement.setInt(k++, fin);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				result.add(prepareUser(resultSet));
			}

		} catch (SQLException e) {
			logger.error("can't get all banned", e);
			throw new MyAppException("something going wrong with db", e);
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
			logger.error("can't ban user", e);
			throw new MyAppException("something going wrong with db", e);
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
			logger.error("cant unblock user", e);
			throw new MyAppException("something going wrong with db", e);
		} finally {
			close(preparedStatement);
			close(connection);
		}
	}

	public List<User> getAllNotBanned(String fio, String email, String diplom, int start, int fin) {
		logger.trace("UserDAO#getAllUsers()"+"f="+fio+"|diplom="+diplom+"|email="+email+"|start="+start+"|fin="+fin);
		List<User> result = new LinkedList<>();

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			int k=1;
			preparedStatement = getPreparedStatement(null, SELECT_ALL_USERS_NOT_BANNED);
			preparedStatement.setString(k++, '%'+fio+'%');
			preparedStatement.setString(k++, '%'+email+'%');
			preparedStatement.setString(k++, '%'+diplom+'%');
			preparedStatement.setInt(k++, start);
			preparedStatement.setInt(k++, fin);
			
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				User user=prepareUser(resultSet);
				int count=new UserDAO().getDepartmentCountForUser(user.getId());
				user.setDepartmentCount(count);
				result.add(user);
			}

		} catch (SQLException e) {
			logger.error("can't get all users", e);
			throw new MyAppException("something going wrong with db", e);
		} finally {
			close(preparedStatement);
			close(resultSet);
			close(connection);
		}
		return result;
	}

	public int getDepartmentCountForUser(int id) {
		logger.trace("UserDAO#getAllUsers()");
		int result=0;
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = getPreparedStatement(null, SELECT_COUNT_FOR_USER);
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				result=resultSet.getInt(1);
			}

		} catch (SQLException e) {
			logger.error("can't get all users", e);
			throw new MyAppException("something going wrong with db", e);
		} finally {
			close(preparedStatement);
			close(resultSet);
			close(connection);
		}
		return result;
	}

	public boolean isBlocked(String userLogin) {
		boolean result = false;

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = getPreparedStatement(null, SELECT_IS_BLOCKED);
			preparedStatement.setString(1, userLogin);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				result = true;
			}

		} catch (SQLException e) {
			logger.error("can't check is user blocked", e);
			throw new MyAppException("something going wrong with db", e);
		} finally {
			close(preparedStatement);
			close(resultSet);
			close(connection);
		}

		logger.trace("checking user " + userLogin + " is blocked=" + result + " in the system");
		return result;
	}

	public boolean exist(String email) {
		logger.debug("checking existing user by email" + email);

		prepareConnectionToWork(connection);
		PreparedStatement prepared = getPreparedStatement(connection, SELECT_USER_BY_EMAIL);
		ResultSet set = null;

		try {
			int k = 1;
			prepared.setString(k++, email);

			set = prepared.executeQuery();

			if (set.next()) {
				logger.info("user with given email "+email+" exist");
				return true;
			}

		} catch (SQLException e) {
			logger.error("can't get result from table user by query " + SELECT_USER_BY_EMAIL, e);
			throw new MyAppException("Something going wrong");
		} finally {
			close(prepared);
			close(set);
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
			user.setFio(resultSet.getString("fio"));
			user.setDiplom(resultSet.getString("diplom"));
			user.setCityArea(resultSet.getString("area"));
			user.setCityId(resultSet.getInt("id_city"));
			user.setRoleId(resultSet.getInt("id_role"));
			user.setBirthday(resultSet.getDate("birthday"));
		} catch (SQLException e) {
			logger.error("can't prepare user", e);
			throw new MyAppException("something going wrong with db", e);
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

	}
