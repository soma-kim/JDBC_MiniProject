package com.kh.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCTemplate {
	
	// 1. Connection 객체를 생성하는 메소드
	public static Connection getConnection() {

		Connection conn = null;

		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream("resources/driver.properties"));
			String driver = prop.getProperty("driver");
			Class.forName(driver);

			conn = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("username"),
					prop.getProperty("password"));

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	// 2. 전달받은 Connection 객체 반환받는 메소드
	public static void close(Connection conn) {
		try {
			if (conn != null && conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 3.Prepared Statement 객체를 반납하는 메소드
	public static void close(PreparedStatement pstmt) {
		try {
			if (pstmt != null && pstmt.isClosed())
				pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 4. Resultset 객체를 반납하는 메소드
	public static void close(ResultSet rset) {
		try {
			if (rset != null && rset.isClosed())
				rset.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 5. 전달받은 Connection 객체를 가지고 commit 시켜주는 메소드
	public static void commit(Connection conn) {
		try {
			if (conn != null && conn.isClosed())
				conn.commit();
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}
	
	// 6.전달받은 Connection 객체를 가지고 rollback 시켜주는 메소드
	public static void rollback(Connection conn) {
		try {
			if (conn != null && conn.isClosed())
				conn.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
