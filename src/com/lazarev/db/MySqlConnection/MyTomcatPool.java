package com.lazarev.db.MySqlConnection;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

public class MyTomcatPool {

	private static Logger logger=Logger.getLogger(MyTomcatPool.class);
	
	private static MyTomcatPool tomcatPool;

	private DataSource datasource;

	public static MyTomcatPool getInstance() {
		if (tomcatPool == null) {
			tomcatPool = new MyTomcatPool();
		}
		return tomcatPool;
	}

	private MyTomcatPool() {

        try {
    		/**
             * Get initial context that has references to all configurations and
             * resources defined for this web application.
             */
        	Context initialContext = new InitialContext();
            
            /**
             * Get Context object for all environment naming (JNDI), such as
             * Resources configured for this web application.
             */
            Context environmentContext = (Context) initialContext
                    .lookup("java:comp/env");
            /**
             * Name of the Resource we want to access.
             */
            String dataResourceName = "jdbc/university_selection_committee";
            /**
             * Get the data source for the MySQL to request a connection.
             */
        	datasource = (DataSource) environmentContext
			        .lookup(dataResourceName);
		} catch (NamingException e) {
			e.printStackTrace();
		}
        
	}

	public Connection getConnection() {
		Connection con = null;
		try {
			/**
	         * Request a Connection from the pool of connection threads.
	         */
			con = datasource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Error "+e.getStackTrace());
		}
		logger.debug("connection from pool returned"+con);
		return con;
	}
}
