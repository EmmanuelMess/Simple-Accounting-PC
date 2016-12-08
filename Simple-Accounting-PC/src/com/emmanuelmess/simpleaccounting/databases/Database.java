package com.emmanuelmess.simpleaccounting.databases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class Database {
	
	private static final String USER_NAME = "admin", PASSWORD = "pass", SEVER_NAME = "localhost", PORT_NUM = "3306", DB_NAME = "data";
	
	protected Connection con;
	
	public Database() throws SQLException {
		con = getConnection();
	}
	
	public void close() {
		if(con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void createTable(String statement, Connection con) {
        try (Statement stat = con.createStatement()){
			stat.executeUpdate(statement);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Connection getConnection() throws SQLException {
	    Connection conn = DriverManager.getConnection("jdbc:sqlite:" + DB_NAME + ".db");
	    System.out.println("Connected to database");
	    return conn;
	}
}
