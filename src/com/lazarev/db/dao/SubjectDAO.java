package com.lazarev.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.lazarev.db.entity.DepartmentSubject;
import com.lazarev.db.entity.Subject;
import com.lazarev.exception.MyAppException;
import com.lazarev.exception.MyDbException;

public class SubjectDAO extends DAO<Subject, Integer> {

	private static final Logger LOGGER = Logger.getLogger(SubjectDAO.class);

	private static final String SELECT_SUBJECT_BY_KEY = "select * from subject where id=?";

	private static final String INSERT_SUBJECT = "insert into subject(name) values(?)";

	private static final String SELECT_SUBJECT_BY_NAME = "select * from subject where name=?";

	private static final String INSERT_DEPARTMENT_SUBJECT = "insert into need_subject_for_department values(?,?,?,?,?)";

	private static final String DELETE_MARKS_BY_DEPARTMENT = "delete from need_subject_for_department where id_dep=?";

	private static final String SELECT_SUBJECT_FOR_DEPARTMET = "SELECT * from `subject`, need_subject_for_department where need_subject_for_department.id_sub=`subject`.id and need_subject_for_department.id_dep=?";

	private static final String SELECT_ALL_SUBJECT_BY_NAME = "SELECT `subject`.`name` FROM `subject` where `subject`.`name` like ?";

	@Override
	public Subject get(Integer key) {
		Subject subject = new Subject();
		prepareConnectionToWork(connection);
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = getPreparedStatement(null, SELECT_SUBJECT_BY_KEY);
			preparedStatement.setInt(1, key);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				subject = prepareSubject(resultSet);
			}

		} catch (SQLException e) {
			LOGGER.error("can't get Subject by id", e);
			throw new MyAppException("something going wrong with db", e);
		} finally {
			close(preparedStatement);
			close(resultSet);
		}

		return subject;
	}

	@Override
	public List<Subject> getAll() {
		return null;
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
		LOGGER.debug("inserting subject" + entity);
		boolean result = false;
		prepareConnectionToWork(connection);
		if (entity != null) {
			PreparedStatement preparedStatement = null;
			ResultSet resultSet = null;
			try {
				preparedStatement = connection.prepareStatement(INSERT_SUBJECT, Statement.RETURN_GENERATED_KEYS);
				preparedStatement.setString(1, entity.getName());
				preparedStatement.executeUpdate();
				resultSet = preparedStatement.getGeneratedKeys();

				if (resultSet.next()) {
					entity.setId(resultSet.getInt(1));
					result = true;
				}

			} catch (SQLException e) {
				LOGGER.error("can't insert Subject", e);
				throw new MyAppException("something going wrong with db", e);
			} finally {
				close(preparedStatement);
				close(resultSet);
			}
		}
		return result;
	}

	// (id will be set from db if exist)
	public void getByName(Subject subject) {
		LOGGER.debug("fin subject by name");
		String name = subject.getName();
		prepareConnectionToWork(connection);
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = getPreparedStatement(null, SELECT_SUBJECT_BY_NAME);
			preparedStatement.setString(1, name);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				subject.setId(resultSet.getInt("id"));
			}

		} catch (SQLException e) {
			LOGGER.error("can't get Subject by name", e);
			throw new MyAppException("something going wrong with db", e);
		} finally {
			close(preparedStatement);
			close(resultSet);
		}
	}

	private boolean insertSubjectForDepartment(int idDepartment, DepartmentSubject departmentSubject) {
		LOGGER.debug("insert subject for department=" + departmentSubject.getId());
		boolean result = false;
		prepareConnectionToWork(connection);

		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(INSERT_DEPARTMENT_SUBJECT);
			int k = 1;
			preparedStatement.setInt(k++, idDepartment);
			preparedStatement.setInt(k++, departmentSubject.getId());
			preparedStatement.setDouble(k++, departmentSubject.getScale());
			preparedStatement.setDouble(k++, departmentSubject.getMaxMark());
			preparedStatement.setBoolean(k++, departmentSubject.getUserEntered());

			int x = preparedStatement.executeUpdate();

			if (x != 0) {
				result = true;
			}

		} catch (SQLException e) {
			LOGGER.error("can't insert Subject for department", e);
			throw new MyAppException("something going wrong with db", e);
		} finally {
			close(preparedStatement);
		}

		return result;

	}

	private boolean deleteMarksFromDepartment(int idDepartment) {
		boolean result = false;
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = getPreparedStatement(connection, DELETE_MARKS_BY_DEPARTMENT);
			preparedStatement.setInt(1, idDepartment);
			int x = preparedStatement.executeUpdate();

			if (x == 1) {
				result = true;
				LOGGER.trace("department marks" + idDepartment + " deleted");
			} else {
				LOGGER.error("department marks not deleted");
			}

		} catch (SQLException e) {
			LOGGER.error("cant delete marks for deparment", e);
			throw new MyAppException("something going wrong with db", e);
		} finally {
			close(preparedStatement);
		}
		return result;
	}

	public void insertDepartmentSubject(Connection connection, int idDepartment, List<DepartmentSubject> subjects)
			throws MyDbException {
		LOGGER.debug("inserting subjects for departments");
		setConnection(connection);
		for (int i = 0; i < subjects.size(); i++) {
			// insert subject or get id
			getByName(subjects.get(i));
			LOGGER.trace("after finding subject id=" + subjects.get(i).getId());
			if (subjects.get(i).getId() == 0) {
				insert(subjects.get(i));
				LOGGER.trace("after inserting subject id=" + subjects.get(i).getId());
			}

			// insert department subject
			if (!insertSubjectForDepartment(idDepartment, subjects.get(i))) {
				throw new MyDbException("cant insert subject for department");
			}
		}

	}

	public void updateDepartmentSubject(Connection connection, int idDepartment, List<DepartmentSubject> subjects)
			throws MyDbException {
		LOGGER.debug("updating subjects for departments");
		setConnection(connection);
		deleteMarksFromDepartment(idDepartment);

		insertDepartmentSubject(connection, idDepartment, subjects);

	}

	public List<DepartmentSubject> getAllSubjectsForDepartmentId(int departmentId) {
		List<DepartmentSubject> subjects = new LinkedList<>();
		prepareConnectionToWork(connection);
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = getPreparedStatement(null, SELECT_SUBJECT_FOR_DEPARTMET);
			preparedStatement.setInt(1, departmentId);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				subjects.add(prepareDepartmentSubject(resultSet));
			}

		} catch (SQLException e) {
			LOGGER.error("can't get Subject by id", e);
			throw new MyAppException("something going wrong with db", e);
		} finally {
			close(preparedStatement);
			close(resultSet);
			close(connection);
		}

		return subjects;

	}

	public List<String> getAllSubjectWithName(String subjectName) {
		LOGGER.debug("SubjectDAO#getAllSubjectWithName()");
		List<String> result = new LinkedList<>();

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = getPreparedStatement(null, SELECT_ALL_SUBJECT_BY_NAME);
			String val = "%" + subjectName + "%";
			LOGGER.info(val);
			preparedStatement.setString(1, val);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				result.add(resultSet.getString("name"));
			}

		} catch (SQLException e) {
			LOGGER.error("can not get Subject by name", e);
			throw new MyAppException("something going wrong with db", e);
		} finally {
			close(preparedStatement);
			close(resultSet);
			close(connection);
		}
		return result;
	}

	///////////////////////////////////////////////
	// prepare

	private Subject prepareSubject(ResultSet resultSet) {
		Subject subject = new Subject();

		try {
			subject.setId(resultSet.getInt("id"));
			subject.setName(resultSet.getString("name"));
		} catch (SQLException e) {
			LOGGER.error("can not prepare subject", e);
			throw new MyAppException("something going wrong with db", e);
		}

		return subject;
	}

	private DepartmentSubject prepareDepartmentSubject(ResultSet resultSet) {
		DepartmentSubject subject = new DepartmentSubject();

		try {
			subject.setId(resultSet.getInt("id"));
			subject.setName(resultSet.getString("name"));
			subject.setScale(resultSet.getDouble("scale"));
			subject.setMaxMark(resultSet.getDouble("max_posible_value"));
			subject.setUserEntered(resultSet.getBoolean("is_user_entered"));
		} catch (SQLException e) {
			LOGGER.error("can not prepare subject", e);
			throw new MyAppException("something going wrong with db", e);
		}

		return subject;
	}
}
