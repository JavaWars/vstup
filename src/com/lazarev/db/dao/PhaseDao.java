package com.lazarev.db.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.log4j.Logger;

import com.lazarev.db.entity.Phase;
import com.lazarev.exception.MyAppException;

public class PhaseDao extends DAO<Phase, Integer> {

	private static final Logger LOGGER = Logger.getLogger(PhaseDao.class);

	private static final String GET_CURRENT_PHASE = "SELECT * FROM phase WHERE phase.is_current_phase=1";
	private static final String SET_CURRENT_PHASE = "call newPhase(?)";
	public void setCurrentPhase(Phase phase) {

		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = getPreparedStatement(null, SET_CURRENT_PHASE);
			preparedStatement.setString(1, phase.getName());
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			LOGGER.error("can't set phase", e);
			throw new MyAppException("something going wrong with db", e);
		} finally {
			close(preparedStatement);
			close(connection);
		}
	}

	public Phase getCurrentPhase() {
		Phase result = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			prepareConnectionToWork(connection);
			statement = connection.createStatement();
			resultSet = statement.executeQuery(GET_CURRENT_PHASE);

			if (resultSet.next()) {
				result=Phase.valueOf(resultSet.getString("name"));
			}

		} catch (SQLException e) {
			LOGGER.error("can't get current phase", e);
			throw new MyAppException("something going wrong with db", e);
		} finally {
			close(statement);
			close(resultSet);
			close(connection);
		}
		return result;
	}

	@Override
	public Phase get(Integer key) {
		return null;
	}

	@Override
	public List<Phase> getAll() {
		return null;
	}

	@Override
	public boolean delete(Integer id) {
		return false;
	}

	@Override
	public boolean update(Phase entity) {
		return false;
	}

	@Override
	public boolean insert(Phase entity) {
		return false;
	}
}
