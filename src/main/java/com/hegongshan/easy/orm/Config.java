package com.hegongshan.easy.orm;

import com.hegongshan.easy.orm.core.DatabaseProvider;
import com.hegongshan.easy.orm.exception.ConfigException;
import com.hegongshan.easy.orm.util.PropertiesUtils;

public final class Config {

	private static final PropertiesUtils PROP = new PropertiesUtils(Constants.CONFIG_PROPERTIES);

	public static DatabaseProvider getDatabaseProvider() {
		String databaseProvider = PROP.getProperty("databaseProvider");
		requireNotNull(databaseProvider,"databaseProvider");
		if ("MySQL".equalsIgnoreCase(databaseProvider)) {
			return DatabaseProvider.MYSQL;
		} else if ("Oracle".equalsIgnoreCase(databaseProvider)) {
			return DatabaseProvider.ORACLE;
		} else if ("SqlServer".equalsIgnoreCase(databaseProvider)) {
			return DatabaseProvider.SQL_SERVER;
		}
		throw new ConfigException("Sorry,easy orm doesn't yet support " + databaseProvider);
	}

	public static String getDriverClassName() {
		String driverClassName = PROP.getProperty("driverClassName");
		requireNotNull(driverClassName, "driverClassName");
		return driverClassName;
	}

	public static String getUrl() {
		String url = PROP.getProperty("url");
		requireNotNull(url, "url");
		return url;
	}

	public static String getUsername() {
		String username = PROP.getProperty("username");
		requireNotNull(username, "username");
		return username;
	}

	public static String getPassword() {
		String password = PROP.getProperty("password");
		requireNotNull(password, "password");
		return password;
	}

	public static String getDataSource() {
		String dataSource = PROP.getProperty("dataSource");
		requireNotNull(dataSource, "dataSource");
		return dataSource;
	}

	private static void requireNotNull(String source, String name) {
		if (source == null) {
			throw new ConfigException(
					"The attribute '" + name + "' is missing in " + Constants.CONFIG_PROPERTIES);
		}
	}
}
