package com.lazarev.db.MySqlConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

public class MyTomcatPool {

	private static Logger logger=Logger.getLogger(MyTomcatPool.class);
	
	private static MyTomcatPool tomcatPool;

	private static DataSource datasource;

	public static MyTomcatPool getInstance() {
		if (tomcatPool == null) {
			tomcatPool = new MyTomcatPool();
		}
		return tomcatPool;
	}

	private MyTomcatPool() {
		PoolProperties p = new PoolProperties();
		p.setUrl("jdbc:mysql://localhost:3306/university_selection_committee?autoReconnect=true&useSSL=true");
		p.setDriverClassName("com.mysql.jdbc.Driver");
		p.setUsername("root");
		p.setPassword("root");
		p.setJmxEnabled(true);
		p.setTestWhileIdle(false);
		p.setTestOnBorrow(true);
		p.setValidationQuery("SELECT 1");
		p.setTestOnReturn(false);
		p.setValidationInterval(30000);
		p.setTimeBetweenEvictionRunsMillis(30000);
		p.setMaxActive(100);
		p.setInitialSize(10);
		p.setMaxWait(10000);
		p.setRemoveAbandonedTimeout(60);
		p.setMinEvictableIdleTimeMillis(30000);
		p.setMinIdle(10);
		p.setLogAbandoned(true);
		p.setRemoveAbandoned(true);
		p.setJdbcInterceptors("org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;"
				+ "org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");
		datasource = new DataSource();
		datasource.setPoolProperties(p);
	}

	public Connection getConnection() {
		Connection con = null;
		
		try {
			Future<Connection> future = datasource.getConnectionAsync();
			while (!future.isDone()) {
				logger.info("Connection is not yet available. Do some background work");
				try {
					Thread.sleep(100); // simulate work
				} catch (InterruptedException x) {
					logger.info("",x);
					Thread.currentThread().interrupt();
				}
			}

			con = future.get();// should return instantly
		
		} catch (InterruptedException |ExecutionException | SQLException e) {
			e.printStackTrace();
			logger.error("Error ");
		}
		logger.debug("connection from pool returnde"+con);
		return con;

	}
}
