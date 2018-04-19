package com.hegongshan.easy.orm;

import com.hegongshan.easy.orm.core.DatabaseProvider;

public final class Constants {
	public static final String CONFIG_PROPERTIES = "easy.properties";
	
	public static final DatabaseProvider DATABASE_PROVIDER = Config.getDatabaseProvider();
}
