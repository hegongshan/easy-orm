package com.hegongshan.easy.orm.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcUtils {
	
	public static void close(Connection conn) {
		if(conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void close(Statement stmt) {
		if(stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void close(ResultSet rs) {
		if(rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void close(ResultSet rs,Statement stmt) {
		close(rs);
		close(stmt);
	}
	
	public static void close(Statement stmt,Connection conn) {
		close(stmt);
		close(conn);
	}
	
	public static void close(ResultSet rs,Statement stmt,Connection conn) {
		close(rs);
		close(stmt);
		close(conn);
	}

}
