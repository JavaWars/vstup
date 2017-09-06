package com.lazarev.db.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.lazarev.db.entity.Department;

public class DepartmentDAO extends DAO<Department,Integer>{

	private static final Logger LOGGER=Logger.getLogger(DepartmentDAO.class);
	private static final String SELECT_ALL_DEPARTMENT = "select * from departments";
	
	@Override
	public Department get(Integer key) {
		return null;
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
	public boolean update(Department entity) {
		return false;
	}

	@Override
	public boolean insert(Department entity) {
		return false;
	}
	
	/////////////////////////////////////////////////////
	//				UTIL	
	private Department prepareDepartment(ResultSet resultSet) {
		Department department=new Department();
		
		try {
			department.setId(resultSet.getInt("id"));
			department.setName(resultSet.getString("name"));
			department.setPlaceDov(resultSet.getInt("place_government"));
			department.setTotaPlace(resultSet.getInt("place_total"));
		} catch (SQLException e) {
			LOGGER.error("CAN NOT PREPARE DEpartment",e);
		}	
		
		return department;
	}

}
