package com.dbevents;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

public class DbAccess {

	public static final Logger logger = Logger.getLogger(DbAccess.class);
	
	private static final String DRIVER = "sun.jdbc.odbc.JdbcOdbcDriver";

	private static final String URL = "jdbc:odbc:phonebook";

	private Connection con = null;

	private static DbAccess instance = null;
	
	private Statement statement = null;
	
	public DbAccess() {
		if (null == instance) {
			try {
				Class.forName(DRIVER);
				con = DriverManager.getConnection(URL);
				statement = con.createStatement();
			} catch (ClassNotFoundException classNotFoundException) {
				System.out.println("Driver not found.");
				logger.error(classNotFoundException);
			} catch (SQLException sqlException) {
				System.out.println("Could not able to connect to db.");
				logger.error(sqlException);
				sqlException.printStackTrace();
			}
		}
	}

	public static DbAccess getInstance() {
		if (null == instance) {
			instance = new DbAccess();
		}
		return instance;
	}

	public ResultSet executeQuery(String sql) {
		ResultSet rs = null;
		try {
			rs = statement.executeQuery(sql);
		} catch (SQLException sqlException) {
			System.out.println("Invalid query"+sqlException);
		}
		return rs;
	}
	
	public int executeUpdate(String sql) {
		try {
			return statement.executeUpdate(sql);
		} catch (SQLException sqlException) {
			System.out.println("Invalid query"+sqlException);
		}
		return -1;
	}
	
	public void closeResultSet(ResultSet rs) {
		try {
			if (null != rs) {
				rs.close();
			}
		} catch (SQLException sqlException) {
			System.out.println("Exception while closing result set.");
		}
	}
}