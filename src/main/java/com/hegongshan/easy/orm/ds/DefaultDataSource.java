package com.hegongshan.easy.orm.ds;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.sql.DataSource;

import com.hegongshan.easy.orm.Constants;
import com.hegongshan.easy.orm.util.PropertiesUtils;

public class DefaultDataSource implements DataSource {
	
	private static List<Connection> pool;
	private int poolMinSize = 5;
	private int poolMaxSize = 20;
	private String driverClassName;
	private String url;
	private String username;
	private String password;
	
	public DefaultDataSource(String propertiesName,boolean pooled) {
		PropertiesUtils prop = new PropertiesUtils(propertiesName);
		driverClassName = prop.getProperty("driverClassName");
		url = prop.getProperty("url");
		username = prop.getProperty("username");
		password = prop.getProperty("password");
		if(pooled) {
			poolMinSize = Integer.parseInt(prop.getProperty("poolMinSize"));
			poolMaxSize = Integer.parseInt(prop.getProperty("poolMaxSize"));
		}
		try {
			init();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public DefaultDataSource(boolean pooled) {
		this(Constants.CONFIG_PROPERTIES,pooled);
	}
	
	public DefaultDataSource() {
		this(false);
	}

	@Override
	public PrintWriter getLogWriter() throws SQLException {
		return DriverManager.getLogWriter();
	}

	@Override
	public void setLogWriter(PrintWriter out) throws SQLException {
		DriverManager.setLogWriter(out);
	}

	@Override
	public void setLoginTimeout(int seconds) throws SQLException {
		DriverManager.setLoginTimeout(seconds);
	}

	@Override
	public int getLoginTimeout() throws SQLException {
		return DriverManager.getLoginTimeout();
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		throw new SQLException(getClass().getName() + " is not a wrapper.");
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return false;
	}

	@Override
	public synchronized Connection getConnection() throws SQLException {
		if(pool == null) {
			init();
		}
		int lastIndex = pool.size() - 1;
		Connection connection = pool.get(lastIndex);
		pool.remove(lastIndex);
		return connection;
	}

	@Override
	public Connection getConnection(String username, String password) throws SQLException {
		return getConnection();
	}
	
	public synchronized void close(Connection connection) {
		if(pool.size() > poolMaxSize) {
			if(connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} else {
			try {
				if(!connection.isClosed()) {
					pool.add(connection);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void init() throws SQLException {
		if(pool == null) {
			pool = new ArrayList<Connection>(poolMinSize);
		}
		try {
			Class.forName(driverClassName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		while(pool.size() < poolMinSize) {
			pool.add(DriverManager.getConnection(url, username, password));
		}
	}

}
