package com.lazarev.db.MySqlConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.lazarev.web.servlets.user.UserImgCatcher;

//this is simple db manager for connection without pool. This class not used in project
public class DbManager {

	private static final Logger LOGGER = Logger.getLogger(DbManager.class);

	private static String url;

	private static String username;

	private static String password;

	private static DbManager dbManager;

	private DbManager() {

		url = "jdbc:mysql://localhost:3306/university_selection_committee?autoReconnect=true&useSSL=true";

		username = "root";

		password = "root";

	}

	public static DbManager getInstance() {
		if (dbManager == null) {
			dbManager = new DbManager();
		}

		return dbManager;
	}

	public Connection getNewConnection() throws SQLException {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			LOGGER.error("error when newInstance() was called");
			e.printStackTrace();
		}
		Connection connection = DriverManager.getConnection(url, username, password);

		return connection;
	}

}
