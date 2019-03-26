package com.lazarev.db.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.lazarev.db.entity.Department;
import com.lazarev.db.entity.StudentMark;
import com.lazarev.db.entity.Subject;
import com.lazarev.exception.MyAppException;
import com.mysql.jdbc.Connection;

public class MarkDAO extends DAO<Subject, Integer> {

	private static final Logger LOGGER = Logger.getLogger(MarkDAO.class);
	private static final String CALL_INSERT_USER_DEPARTMEN = "call student_enter(?,?)";
	private static final String CALL_INSERT_USER_MARK = "call student_mark_enter(?,?,?)";
	private static final String SELECT_USER_MARK_BY_USER_ID = "SELECT `subject`.id, `subject`.`name`, student_mark.mark FROM student_mark INNER JOIN `subject` ON student_mark.id_subject = `subject`.id where student_mark.id_stud=?";
	private static final String SELECT_USER_MARKS_WITH_NOT_WRITED_YET = "call AllUserMarks(?)";
	private static final String INSERT_CONFIRMED_USER_MARK = "call insert_confirmed_user_mark(?,?,?)";

	@Override
	public Subject get(Integer key) {
		return null;
	}

	@Override
	public List<Subject> getAll() {
		return null;
	}

	public List<StudentMark> getAll(int userId) {
		List<StudentMark> result = new LinkedList<>();
		LOGGER.debug("get user marks for user id=" + userId);

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = getPreparedStatement(connection, SELECT_USER_MARK_BY_USER_ID);
			preparedStatement.setInt(1, userId);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				result.add(prepareUserMark(resultSet));
			}

		} catch (SQLException e) {
			LOGGER.error("can not get department", e);
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
	public boolean update(Subject entity) {
		return false;
	}

	@Override
	public boolean insert(Subject entity) {
		return false;
	}

	private boolean insertUserDepartment(int idUser, int idDepartment) {
		LOGGER.debug("insertUserDepartment");
		boolean result = true;

		PreparedStatement preparedStatement = getPreparedStatement(connection, CALL_INSERT_USER_DEPARTMEN);
		try {
			preparedStatement.setInt(1, idUser);
			preparedStatement.setInt(2, idDepartment);
			int x = preparedStatement.executeUpdate();
			if (x != 1) {
				result = false;
			}
		} catch (SQLException e) {
			LOGGER.error("can not enter user for this university department" + idUser + "" + idDepartment, e);
			result = false;
			throw new MyAppException("something going wrong with db", e);
		} finally {
			close(preparedStatement);
		}
		LOGGER.debug("insertUserDepartment result" + result);
		return result;
	}

	private boolean insertUserMark(int userId, StudentMark studentMark) {
		LOGGER.debug("inserting user mark for deparment " + userId + " studentMark " + studentMark);
		boolean result = true;
		PreparedStatement preparedStatement = getPreparedStatement(connection, CALL_INSERT_USER_MARK);
		try {
			System.out.println(userId + "|" + studentMark.getId() + "|" + studentMark.getMark());
			preparedStatement.setInt(1, userId);
			preparedStatement.setInt(2, studentMark.getId());
			preparedStatement.setDouble(3, studentMark.getMark());
			int x = preparedStatement.executeUpdate();
			if (x != 1) {
				result = false;
			}
		} catch (SQLException e) {
			LOGGER.error("can not enter user mark for this university department", e);
			result = false;
			throw new MyAppException("something going wrong with db", e);
		} finally {
			close(preparedStatement);
		}
		LOGGER.debug("inserting User Mark result=" + result);
		return result;
	}

	public void insertUserMarks(Department department, List<StudentMark> marks, String userLogin) {
		LOGGER.debug("user insert marks for department " + department.getId() + " ,marks size=" + marks.size());

		boolean ok = true;
		prepareConnectionToWork(connection);
		try {
			connection.setAutoCommit(false);
			connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

			UserDAO userDB = new UserDAO();
			userDB.setConnection(connection);
			int userId = userDB.getIdByEmail(userLogin);

			if (insertUserDepartment(userId, department.getId())) {
				for (int i = 0; i < marks.size(); i++) {
					if (!insertUserMark(userId, marks.get(i))) {
						ok = false;
					}
				}
			} else {
				ok = false;
			}

			if (!ok) {
				LOGGER.error("transaction fail");
				rollback(connection);
			} else {
				LOGGER.error("transaction ok");
				connection.commit();
			}

		} catch (SQLException e) {
			rollback(connection);
			LOGGER.error("can not insert new department", e);
			throw new MyAppException("something going wrong with db", e);

		} finally {
			close(connection);
		}
	}

	private StudentMark prepareUserMark(ResultSet resultSet) {
		StudentMark sm = null;
		try {
			sm = new StudentMark();
			sm.setName(resultSet.getString("name"));
			sm.setMark(resultSet.getDouble("mark"));
		} catch (SQLException e) {
			LOGGER.debug("can not prepare user mark", e);
			sm = null;
			throw new MyAppException("something going wrong with db", e);
		}
		return sm;
	}

	public List<StudentMark> getAllMarksForUserWithId(int id) {
		List<StudentMark> result = new LinkedList();

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = getPreparedStatement(null, SELECT_USER_MARKS_WITH_NOT_WRITED_YET);
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				StudentMark sm = new StudentMark();
				sm.setId(resultSet.getInt("id"));
				sm.setMark(resultSet.getDouble("userMark"));
				sm.setName(resultSet.getString("name"));
				result.add(sm);
			}

		} catch (SQLException e) {
			LOGGER.error("can't get all users", e);
			throw new MyAppException("something going wrong with db", e);
		} finally {
			close(preparedStatement);
			close(resultSet);
			close(connection);
		}

		return result;
	}

	public void insertUserMark(int userId, int subjectId, double mark) {
		LOGGER.debug("inserting user mark for deparment " + userId + " studentMark " + mark+" subjectId " +subjectId);
		
		PreparedStatement preparedStatement = getPreparedStatement(connection, INSERT_CONFIRMED_USER_MARK);
		try {
			preparedStatement.setInt(1, userId);
			preparedStatement.setInt(2, subjectId);
			preparedStatement.setDouble(3, mark);
			int x = preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			LOGGER.error("can not enter user mark for this university department", e);
			throw new MyAppException("something going wrong with db", e);
		} finally {
			close(preparedStatement);
			close(connection);
		}

	}
}
