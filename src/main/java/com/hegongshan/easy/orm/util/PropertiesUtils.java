package com.hegongshan.easy.orm.util;

import java.io.IOException;
import java.util.Properties;

public final class PropertiesUtils {
	private static final Properties PROP = new Properties();
	public PropertiesUtils(String properties) {
		init(properties);
	}
	
	private static void init(String properties) {
		try {
			PROP.load(PropertiesUtils.class.getClassLoader().getResourceAsStream(properties));
		} catch (IOException e) {
			new RuntimeException("[easy-generator] 导入配置文件发生异常！",e);
		}
	}
	
	public String getProperty(String key) {
		return PROP.getProperty(key);
	}
}
