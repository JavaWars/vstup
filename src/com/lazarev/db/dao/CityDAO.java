package com.lazarev.db.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.lazarev.db.entity.City;
import com.lazarev.exception.MyAppException;
import com.mysql.jdbc.Statement;

public class CityDAO extends DAO<City, Integer> {

	private static final Logger LOGGER = Logger.getLogger(CityDAO.class);

	private static final String GET_CITY_ID_BY_NAME = "SELECT cities.id FROM cities WHERE cities.`name`=?";

	private static final String INSERT_CITY = "insert into cities (name) values(?)";

	private static final String SELECT_ALL_CITY_BY_NAME = "select * from cities where name like ?";

	@Override
	public City get(Integer key) {
		return null;
	}

	@Override
	public List<City> getAll() {
		return null;
	}

	@Override
	public boolean delete(Integer id) {
		return false;
	}

	@Override
	public boolean update(City entity) {
		return false;
	}

	@Override
	public boolean insert(City entity) {
		ResultSet resultSet = null;

		prepareConnectionToWork(connection);
		PreparedStatement prepared=null;
		try {
			prepared=connection.prepareStatement(INSERT_CITY,Statement.RETURN_GENERATED_KEYS);
			
			prepared.setString(1, entity.getName());

			prepared.executeUpdate();
			resultSet = prepared.getGeneratedKeys();
			if (resultSet.next()) {
				entity.setId(resultSet.getInt(1));
				return true;
			}
		} catch (SQLException e) {

			LOGGER.error("cant insert city using QUERY: " + INSERT_CITY, e);
			throw new MyAppException("something going wrong with db",e);

		} finally {
			close(prepared);
			close(resultSet);
			close(connection);
		}
		return false;
	}


	public int get(String cityName) {
		City city = new City();

		//if exist
		
		ResultSet resultSet = null;

		PreparedStatement prepared = getPreparedStatement(null, GET_CITY_ID_BY_NAME);
		try {
			prepared.setString(1, cityName);

			resultSet = prepared.executeQuery();

			//contains in db
			if (resultSet.next()) {
				city.setId(resultSet.getInt(1));
			}
			else{
				//need to insert
				city.setName(cityName);
				this.insert(city);
			}
		} catch (SQLException e) {

			LOGGER.error("cant getCityIdByName using QUERY: " + GET_CITY_ID_BY_NAME, e);
			throw new MyAppException("something going wrong with db",e);

		} finally {
			close(prepared);
			close(resultSet);
			close(connection);
		}

		
		return city.getId();
	}

	public List<String> getAllCitysWithName(String cityName) {
		LOGGER.debug("CityDAO#getAllCitysWithName()");
		List<String> result = new LinkedList<>();

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = getPreparedStatement(null, SELECT_ALL_CITY_BY_NAME);
			String val="%"+cityName+"%";
			LOGGER.info(val);
			preparedStatement.setString(1, val);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				result.add(resultSet.getString("name"));
			}

		} catch (SQLException e) {
			LOGGER.error("can not get Departments  with name",e);
			throw new MyAppException("something going wrong with db",e);
		} finally {
			close(preparedStatement);
			close(resultSet);
			close(connection);
		}
		return result;

	}

}
