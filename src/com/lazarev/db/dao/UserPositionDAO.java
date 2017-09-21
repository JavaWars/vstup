package com.lazarev.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.lazarev.db.entity.extra.UserPosition;
import com.lazarev.db.entity.extra.UserTotalMark;
import com.lazarev.exception.MyAppException;
import com.lazarev.web.servlets.helper.ImgConverter;

public class UserPositionDAO extends DAO<UserPosition, Integer> {

	private static final Logger LOGGER = Logger.getLogger(UserPositionDAO.class);
	private static final String SELECT_DEPARTMENT_FOR_USER = "SELECT departments.* FROM student_department INNER JOIN departments ON student_department.id_department = departments.id where id_student=?";
	private static final String SELECT_TOTAL_PLACES_IN_DEPARTMENT = "SELECT COUNT(1) FROM student_department where id_department=id_department and student_department.id_department=?";
	private static final String CALL_SELECT_MY_POSITION_IN_DEPARTMENT = "call getMyPosition(?,?)";
	private static final String CALL_SELECT_DEPARTMENT_RATING_BY_DEP = "call getUsersPassedDockForDep(?)";

	@Override
	public UserPosition get(Integer key) {
		return null;
	}

	@Override
	public List<UserPosition> getAll() {
		return null;
	}

	public List<UserTotalMark> getUsersForDepartmentRating(Integer departmentId) {
		LOGGER.debug("get  department rating for departmentId=" + departmentId);
		List<UserTotalMark> result = new LinkedList<>();

		prepareConnectionToWork(connection);
		PreparedStatement preparedStatement = getPreparedStatement(connection, CALL_SELECT_DEPARTMENT_RATING_BY_DEP);
		ResultSet resultSet = null;
		try {
			preparedStatement.setInt(1, departmentId);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				UserTotalMark userTotalMark = new UserTotalMark();
				prepareUserTotalMark(resultSet, userTotalMark);
				result.add(userTotalMark);
			}

		} catch (SQLException e) {
			LOGGER.error("can not insert new department", e);
			throw new MyAppException("something going wrong with db", e);
		} finally {
			close(connection);
		}

		return result;

	}

	public List<UserPosition> getAll(Integer userId) {
		LOGGER.debug("get  user position for user id=" + userId);
		List<UserPosition> result = null;

		prepareConnectionToWork(connection);
		try {
			connection.setAutoCommit(false);
			connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			result = getByUserId(userId);
			for (int i = 0; i < result.size(); i++) {
				setMyPosition(result.get(i), userId);
				setTotalPlaces(result.get(i));
			}
			connection.commit();

		} catch (SQLException e) {
			rollback(connection);
			LOGGER.error("can not insert new department", e);
			throw new MyAppException("something going wrong with db", e);
		} finally {
			close(connection);
		}

		return result;

	}

	@Override
	public boolean delete(Integer id) {
		return false;
	}

	@Override
	public boolean update(UserPosition entity) {
		return false;
	}

	@Override
	public boolean insert(UserPosition entity) {
		return false;
	}

	private List<UserPosition> getByUserId(int userId) {
		LOGGER.debug("DepartmentDAO#getByUserId()");
		List<UserPosition> result = new LinkedList<>();

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = getPreparedStatement(connection, SELECT_DEPARTMENT_FOR_USER);
			preparedStatement.setInt(1, userId);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				result.add(prepareUserPosition(resultSet));
			}

		} catch (SQLException e) {
			LOGGER.error("can not get all departmens");
			throw new MyAppException("something going wrong with db", e);
		} finally {
			close(preparedStatement);
			close(resultSet);
		}

		return result;
	}

	private void setTotalPlaces(UserPosition userPosition) {
		// getId==department
		LOGGER.debug("set total places for dep" + userPosition.getId());

		PreparedStatement preparedStatement = getPreparedStatement(connection, SELECT_TOTAL_PLACES_IN_DEPARTMENT);
		ResultSet resultSet = null;
		try {
			preparedStatement.setInt(1, userPosition.getId());

			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				userPosition.setTotalPeople(resultSet.getInt(1));
			}
		} catch (SQLException e) {
			LOGGER.error("can not get count of users for department" + userPosition.getId(), e);
			throw new MyAppException("something going wrong with db", e);
		} finally {
			close(resultSet);
			close(preparedStatement);
		}

	}

	private void setMyPosition(UserPosition userPosition, Integer userId) {
		LOGGER.debug("set my position for user" + userId);
		PreparedStatement preparedStatement = getPreparedStatement(connection, CALL_SELECT_MY_POSITION_IN_DEPARTMENT);
		ResultSet resultSet = null;
		try {
			preparedStatement.setInt(1, userPosition.getId());// department id
			preparedStatement.setInt(2, userId);

			preparedStatement.executeUpdate();
			resultSet = preparedStatement.getResultSet();
			while (resultSet.next()) {
				userPosition.setMyPlace(resultSet.getInt(1));
			}
		} catch (SQLException e) {
			LOGGER.error("can not position for user" + userId, e);
			throw new MyAppException("something going wrong with db", e);
		} finally {
			close(resultSet);
			close(preparedStatement);
		}
	}

	/////////////////////////////////////////////////////
	// UTIL
	private UserPosition prepareUserPosition(ResultSet resultSet) {
		UserPosition position = new UserPosition();

		try {
			position.setId(resultSet.getInt("id"));
			position.setName(resultSet.getString("name"));
			position.setPlaceGov(resultSet.getInt("place_government"));
			position.setTotaPlace(resultSet.getInt("place_total"));
		} catch (SQLException e) {
			LOGGER.error("CAN NOT PREPARE user Position (information about department)", e);
			throw new MyAppException("something going wrong with db", e);
		}

		return position;
	}

	private void prepareUserTotalMark(ResultSet resultSet, UserTotalMark userTotalMark) throws SQLException {
		userTotalMark.setId(resultSet.getInt("id"));// userId
		userTotalMark.setName(resultSet.getString("name"));
		userTotalMark.setSecondName(resultSet.getString("secondName"));
		userTotalMark.setMark(resultSet.getDouble("mark"));
		userTotalMark.setFile(ImgConverter.fileNameTo64BaseData(String.valueOf(userTotalMark.getId())));
	}
}
