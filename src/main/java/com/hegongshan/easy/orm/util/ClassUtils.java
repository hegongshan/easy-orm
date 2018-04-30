package com.hegongshan.easy.orm.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class ClassUtils {

	private static final Logger LOG = Logger.getLogger(ClassUtils.class.getName());

	public static Object newInstance(String className) {
		try {
			return Class.forName(className).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			LOG.log(Level.SEVERE, e.getMessage(), e);
			return null;
		}
	}
	
	public static void setField(Object obj, Field field, Object value) {
		field.setAccessible(true);
		try {
			field.set(obj, value);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			LOG.log(Level.SEVERE, e.getMessage(), e);
		}
	}
	
	public static Object getField(Object obj, Field field) {
		field.setAccessible(true);
		try {
			return field.get(obj);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			LOG.log(Level.SEVERE, e.getMessage(), e);
			return null;
		}
	}
	
	public static Field getDeclaredField(Class<?> clazz,String field) {
		try {
			return clazz.getDeclaredField(field);
		} catch (NoSuchFieldException | SecurityException e) {
			LOG.log(Level.SEVERE, e.getMessage(), e);
			return null;
		}
	}
	
	public static boolean isFinal(Field field) {
		return Modifier.isFinal(field.getModifiers());
	}
	
	public static boolean isCustomizedDomain(Class<?> clazz) {
		Class<?>[] regularTypes = {
						byte.class,Byte.class,
						short.class,Short.class,
						int.class,Integer.class,
						long.class,Long.class,
						float.class,Float.class,
						double.class,Double.class,
						char.class,Character.class,
						boolean.class,Boolean.class,
						
						String.class,
						
						BigDecimal.class,BigInteger.class,
						
						Blob.class,Clob.class,
						java.util.Date.class,java.sql.Date.class,
						Timestamp.class,
						Time.class,
						
						Collection.class,List.class,Set.class,Map.class};
		return !equals(clazz,regularTypes);
	}
	private static boolean equals(Class<?> clazz,Class<?> ... others) {
		for(Class<?> other : others) {
			if(clazz == other)
				return true;
		}
		return false;
	}
}
