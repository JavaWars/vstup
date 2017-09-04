package com.lazarev.db.MySqlConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbManager {
	
	private static String url;

    private static String username;

    private static String password;

    private static DbManager dbManager;

	
	private DbManager() {
//        Properties props = new Properties();
//        try {
//            props.load(new FileInputStream("app.properties"));
//        } catch (IOException ex) {
//            throw new IllegalStateException("app.properties not found!", ex);
//        }
        url = "jdbc:mysql://localhost:3306/university_selection_committee?autoReconnect=true&useSSL=true";

        username = "root";

        password = "root";

    }

    public static DbManager getInstance(){
        if (dbManager==null){
            dbManager=new DbManager();
        }

        return dbManager;
    }

    public Connection getNewConnection() throws SQLException {
    	try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        Connection connection=DriverManager.getConnection(url,username,password);

        return connection;
    }

}
