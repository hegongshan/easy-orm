package com.hegongshan.easy.orm.ds;

import javax.sql.DataSource;

public class DataSourceFactory {
	public static DataSource ds;

	public static DataSource getDataSource() {
		if (ds == null)
			return new DefaultDataSource();
		else
			return ds;
	}

	public static void setDataSource(DataSource ds) {
		DataSourceFactory.ds = ds;
	}

}
