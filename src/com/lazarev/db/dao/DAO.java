package com.lazarev.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.log4j.Logger;

import com.lazarev.db.MySqlConnection.MyTomcatPool;
import com.lazarev.exception.MyAppException;

//E=entity
//K=key
public abstract class DAO<E, K> {

	private static Logger logger = Logger.getLogger(DAO.class);

	protected Connection connection = null;

	////////////////////////////////////////
	// CRUD operations
	public abstract E get(K key);

	public abstract List<E> getAll();

	public abstract boolean delete(K id);

	public abstract boolean update(E entity);

	public abstract boolean insert(E entity);

	//////////////////////////////////////////
	// UTIL

	protected Statement getStatement(Connection connection) {

		prepareConnectionToWork(connection);

		Statement statement = null;
		try {
			statement = connection.createStatement();
		} catch (SQLException e) {

			logger.error("cant create statement", e);
			throw new MyAppException("something EXTREMELY BAD HAPPENED with db", e);

		}
		logger.trace("statement created");
		return statement;
	}

	protected PreparedStatement getPreparedStatement(Connection connection, String sql) {

		prepareConnectionToWork(connection);

		PreparedStatement prepared = null;
		try {

			prepared = this.connection.prepareStatement(sql);

		} catch (SQLException e) {

			logger.error("cant create prepared statement", e);
			throw new MyAppException("something EXTREMELY BAD HAPPENED with db", e);

		}
		logger.trace("PreparedStatement created");
		return prepared;
	}

	protected void prepareConnectionToWork(Connection connection) {

		if (this.connection == null) {
			if (connection == null) {
				this.connection = MyTomcatPool.getInstance().getConnection();
			} else {
				this.connection = connection;
			}
		}
	}

	protected void close(AutoCloseable closeRes) {
		try {
			closeRes.close();
		} catch (Exception e) {
			logger.error("Cannot close a resource: " + closeRes, e);
			throw new MyAppException("something EXTREMELY BAD HAPPENED with db", e);
		}
	}

	protected void rollback(Connection con) {
		if (con != null) {
			try {
				con.rollback();
			} catch (SQLException e) {
				logger.error("Cannot rollback a resource: " + con, e);
				throw new MyAppException("something EXTREMELY BAD HAPPENED with db", e);
			}
		}
	}

	void setConnection(Connection connection) {
		this.connection = connection;
	}
}
