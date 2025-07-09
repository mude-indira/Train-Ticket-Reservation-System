package com.shashi.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.shashi.beans.TrainException;
import com.shashi.constant.ResponseCode;

public class DBUtil {
	private static Connection con;

	static {

		// Load from application.properties (fallback)
		ResourceBundle rb = ResourceBundle.getBundle("application");

		String url = System.getenv().getOrDefault("DB_URL", rb.getString("connectionString"));
		String username = System.getenv().getOrDefault("DB_USERNAME", rb.getString("username"));
		String password = System.getenv().getOrDefault("DB_PASSWORD", rb.getString("password"));
		String driver = System.getenv().getOrDefault("DB_DRIVER", rb.getString("driverName"));

		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			System.out.println("DB URL: " + url);
			System.out.println("User: " + username);
			con = DriverManager.getConnection(url, username, password);
			System.out.println("Connection Success!!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() throws TrainException {
		if (con == null)
			throw new TrainException(ResponseCode.DATABASE_CONNECTION_FAILURE);
		return con;
	}
}
