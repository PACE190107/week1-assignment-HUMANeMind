package com.revature.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCConnectionUtil {

	// JDBC - Java Data Base Connectivity
	static 
	{
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() throws SQLException {
		Properties prop = new Properties();
		InputStream input = null;
		String url = null;
		String user = null;
		String pass = null;
		
		try {
			input = JDBCConnectionUtil.class.getClassLoader().getResourceAsStream("config.properties");

			// load a properties file
			prop.load(input);

			// get the property value and set them to variables
			url = prop.getProperty("url");
			user = prop.getProperty("user");
			pass = prop.getProperty("pass");

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		// Establish connection using jdbc
		Connection conn = DriverManager.getConnection(url, user, pass);

		return conn;
	}
}
