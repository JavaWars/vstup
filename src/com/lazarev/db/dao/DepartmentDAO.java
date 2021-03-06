package com.lazarev.db.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.lazarev.db.entity.Department;
import com.lazarev.db.entity.DepartmentSubject;
import com.lazarev.exception.MyAppException;
import com.lazarev.exception.MyDbException;
import com.mysql.jdbc.Connection;

public class DepartmentDAO extends DAO<Department, Integer> {

	private static final Logger LOGGER = Logger.getLogger(DepartmentDAO.class);
	private static final String SELECT_ALL_DEPARTMENT = "select * from departments";
	private static final String INSERT_DEPARTMNET = "insert into departments values(default,?,?,?)";
	private static final String DELETE_DEPARTMNET = "delete from departments where departments.id=?";
	private static final String UPDATE_DEPARTMENT = "UPDATE departments SET name = ?, place_government=?, place_total=? where id=?";
	private static final String SELECT_DEPARTMENT_BY_ID = "select * from departments where id=?";
	private static final String SELECT_ALL_DEPARTMENT_WITH_NAME = "select * from departments where name like ?";
	private static final String UPDATE_PRIORITY = "update student_department set priority=? where id_student=? and id_department=?";

	@Override
	public Department get(Integer key) {
		Department result = new Department();

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = getPreparedStatement(connection, SELECT_DEPARTMENT_BY_ID);
			preparedStatement.setInt(1, key);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				setDepartment(resultSet, result);
			} else {
				throw new MyAppException("can't find department");
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
	public List<Department> getAll() {
		LOGGER.debug("DepartmentDAO#getAllUsers()");
		List<Department> result = new LinkedList<>();

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = getPreparedStatement(null, SELECT_ALL_DEPARTMENT);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				result.add(prepareDepartment(resultSet));
			}

		} catch (SQLException e) {
			LOGGER.error("can not get all departmens");
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
		boolean result = false;

		PreparedStatement preparedStatement = getPreparedStatement(connection, DELETE_DEPARTMNET);
		try {
			int k = 1;
			preparedStatement.setInt(k++, id);

			if (preparedStatement.executeUpdate() == 1) {
				result = true;
			}
		} catch (SQLException e) {
			LOGGER.debug("can not insert department", e);
			throw new MyAppException("something going wrong with db", e);
		} finally {
			close(preparedStatement);
			close(connection);
		}

		return result;
	}

	@Override
	public boolean update(Department entity) {
		LOGGER.debug("update new department");
		boolean result = false;

		PreparedStatement preparedStatement = getPreparedStatement(connection, UPDATE_DEPARTMENT);
		try {
			int k = 1;
			preparedStatement.setString(k++, entity.getName());
			preparedStatement.setInt(k++, entity.getPlaceGov());
			preparedStatement.setInt(k++, entity.getTotaPlace());
			preparedStatement.setInt(k++, entity.getId());

			if (preparedStatement.executeUpdate() == 1) {
				result = true;
			}
		} catch (SQLException e) {
			LOGGER.debug("can not update department", e);
			throw new MyAppException("something going wrong with db", e);
		} finally {
			close(preparedStatement);
		}

		return result;
	}

	@Override
	public boolean insert(Department entity) {
		LOGGER.debug("inser new department");
		boolean result = false;

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = connection.prepareStatement(INSERT_DEPARTMNET, Statement.RETURN_GENERATED_KEYS);
			int k = 1;
			preparedStatement.setString(k++, entity.getName());
			preparedStatement.setInt(k++, entity.getPlaceGov());
			preparedStatement.setInt(k++, entity.getTotaPlace());

			if (preparedStatement.executeUpdate() == 1) {
				resultSet = preparedStatement.getGeneratedKeys();
				if (resultSet.next()) {
					entity.setId(resultSet.getInt(1));
					result = true;
				}
			}
		} catch (SQLException e) {
			LOGGER.debug("can not insert department", e);
			throw new MyAppException("something going wrong with db", e);
		} finally {
			close(resultSet);
			close(preparedStatement);
		}

		return result;
	}

	// this method use transaction
	public void insertDepartment(Department department, List<DepartmentSubject> marks) {
		LOGGER.debug("insert new department with marks called");
		if (marks.size() > 0) {
			prepareConnectionToWork(connection);
			try {
				connection.setAutoCommit(false);
				connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

				// insert department
				if (!insert(department)) {
					rollback(connection);
				} else {
					// insert department subject
					try {
						new SubjectDAO().insertDepartmentSubject(connection, department.getId(), marks);
					} catch (MyDbException e) {
						LOGGER.error("can not complite transaction", e);
						rollback(connection);
						throw new MyAppException("something going wrong with db", e);
					}
				}
				connection.commit();

			} catch (SQLException e) {
				rollback(connection);
				LOGGER.error("can not insert new department", e);
				throw new MyAppException("something going wrong with db", e);
			} finally {
				close(connection);
			}
		} else {
			LOGGER.error("incorrect input data");
			throw new MyAppException("incorrect input data");

		}
	}

	public void udateDepartment(Department department, List<DepartmentSubject> marks) {
		LOGGER.debug("insert new department with marks called");
		if (marks.size() > 0) {
			prepareConnectionToWork(connection);
			try {
				connection.setAutoCommit(false);
				connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

				// insert department
				if (!update(department)) {//
					rollback(connection);
				} else {
					// insert department subject
					try {
						new SubjectDAO().updateDepartmentSubject(connection, department.getId(), marks);
					} catch (MyDbException e) {
						LOGGER.error("can not complite transaction", e);
						rollback(connection);
					}
				}
				connection.commit();

			} catch (SQLException e) {
				rollback(connection);
				LOGGER.error("can not insert new department", e);
				throw new MyAppException("something going wrong with db", e);
			} finally {
				close(connection);
			}
		} else {
			LOGGER.error("incorrect input data");
		}

	}

	public List<Department> getAllDepartmnetsWithName(String departmantName) {
		LOGGER.debug("DepartmentDAO#getAllDepartmnetsWithName()");
		List<Department> result = new LinkedList<>();

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = getPreparedStatement(null, SELECT_ALL_DEPARTMENT_WITH_NAME);
			String val = "%" + departmantName + "%";
			LOGGER.info(val);
			preparedStatement.setString(1, val);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				result.add(prepareDepartment(resultSet));
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

	public void updateUserPriorityList(int userId, List<Department> priorityList) {

		LOGGER.trace("update user priority list");

		prepareConnectionToWork(connection);
		try {
			connection.setAutoCommit(false);
			connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

			int i = 0;
			boolean ok = true;
			for (Department d : priorityList) {
				i++;

				PreparedStatement preparedStatement = null;
				try {
					preparedStatement = getPreparedStatement(null, UPDATE_PRIORITY);
					int k = 1;
					preparedStatement.setInt(k++, i);
					preparedStatement.setInt(k++, userId);
					preparedStatement.setInt(k++, d.getId());
					preparedStatement.executeUpdate();
				} catch (Exception e) {
					LOGGER.trace("update user priority list erroe" + e);
					rollback(connection);
					ok = false;
					break;
				}
			}

			if (ok)
				connection.commit();

		} catch (SQLException e) {
			rollback(connection);
			LOGGER.error("can not insert new department", e);
			throw new MyAppException("something going wrong with db", e);
		} finally {
			close(connection);
		}
	}

	/////////////////////////////////////////////////////
	// UTIL
	private Department prepareDepartment(ResultSet resultSet) {
		Department department = new Department();

		try {
			department.setId(resultSet.getInt("id"));
			department.setName(resultSet.getString("name"));
			department.setPlaceGov(resultSet.getInt("place_government"));
			department.setTotaPlace(resultSet.getInt("place_total"));
		} catch (SQLException e) {
			LOGGER.error("CAN NOT PREPARE Department", e);
			throw new MyAppException("something going wrong with db", e);
		}

		return department;
	}

	private void setDepartment(ResultSet resultSet, Department department) {

		try {
			department.setId(resultSet.getInt("id"));
			department.setName(resultSet.getString("name"));
			department.setPlaceGov(resultSet.getInt("place_government"));
			department.setTotaPlace(resultSet.getInt("place_total"));
		} catch (SQLException e) {
			LOGGER.error("CAN NOT PREPARE Department", e);
			throw new MyAppException("something going wrong with db", e);
		}
	}

}
