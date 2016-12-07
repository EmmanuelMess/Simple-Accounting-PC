package com.emmanuelmess.simpleaccounting.databases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public abstract class Database {
	
	private static final String USER_NAME = "admin", PASSWORD = "pass", SEVER_NAME = "localhost", PORT_NUM = "3306";
	
	public Connection getConnection(final String TABLE_NAME) throws SQLException {
	    Connection conn = null;
	    Properties connectionProps = new Properties();
	    connectionProps.put("user", USER_NAME);
	    connectionProps.put("password", PASSWORD);

        conn = DriverManager.getConnection("jdbc:mysql://" + SEVER_NAME + ":"
        			+ PORT_NUM + "/" + TABLE_NAME, connectionProps);
	    System.out.println("Connected to database");
	    return conn;
	}
}
