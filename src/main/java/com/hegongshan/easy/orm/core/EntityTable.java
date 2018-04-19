package com.hegongshan.easy.orm.core;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.hegongshan.easy.orm.annotation.Column;
import com.hegongshan.easy.orm.annotation.Id;
import com.hegongshan.easy.orm.annotation.Table;
import com.hegongshan.easy.orm.util.ClassUtils;
import com.hegongshan.easy.orm.util.StringUtils;

public class EntityTable {
	public static Map<String, Object> getIds(Object obj) {
		Map<String, Object> ids = new LinkedHashMap<String, Object>();
		Field[] fields = obj.getClass().getDeclaredFields();
		for (Field field : fields) {
			if (!isId(field)) {
				continue;
			}
			String idName = null;
			Id id = field.getAnnotation(Id.class);
			if (StringUtils.isEmpty(id.value())) {
				idName = StringUtils.camelCaseToUnderline(field.getName());
			} else {
				idName = id.value();
			}
			ids.put(idName, ClassUtils.getField(obj, field));
		}
		return ids;
	}

	public static Set<String> getIdNames(Object obj) {

		return getIdNames(obj.getClass());
	}

	public static Set<String> getIdNames(Class<?> clazz) {
		Set<String> idNames = new LinkedHashSet<String>(clazz.getDeclaredFields().length);
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			if (!isId(field)) {
				continue;
			}
			String idName = null;
			Id id = field.getAnnotation(Id.class);
			if (StringUtils.isEmpty(id.value())) {
				idName = StringUtils.camelCaseToUnderline(field.getName());
			} else {
				idName = id.value();
			}
			idNames.add(idName);
		}
		return idNames;
	}

	public static boolean isId(Object obj, String fieldName) {
		return isId(ClassUtils.getDeclaredField(obj.getClass(), fieldName));
	}

	public static boolean isId(Field field) {
		return field.isAnnotationPresent(Id.class);
	}

	public static String getIdName(Object obj, Field field) {
		return getIdName(obj.getClass(), field);
	}

	public static String getIdName(Class<?> clazz, Field field) {
		if (!isId(field))
			throw new RuntimeException(field.getName() + " is not a primary key");
		String idName = field.getAnnotation(Id.class).value();
		return idName.isEmpty() ? idName = StringUtils.camelCaseToUnderline(field.getName()) : idName;
	}

	public static String getIdName(Object obj, String fieldName) {
		return getIdName(obj.getClass(), fieldName);
	}

	public static String getIdName(Class<?> clazz, String fieldName) {
		Field field = ClassUtils.getDeclaredField(clazz, fieldName);
		return getIdName(clazz, field);
	}

	public static List<Object> getIdValues(Object obj) {
		List<Object> idValues = new ArrayList<Object>();
		Field[] fields = obj.getClass().getDeclaredFields();
		for (Field field : fields) {
			if (!isId(field)) {
				continue;
			}
			idValues.add(ClassUtils.getField(obj, field));
		}
		return idValues;
	}

	public static boolean isColumn(Object obj, String fieldName) {
		return isColumn(obj.getClass(), fieldName);
	}

	public static boolean isColumn(Class<?> clazz, String fieldName) {
		try {
			return isColumn(clazz.getDeclaredField(fieldName));
		} catch (NoSuchFieldException | SecurityException e) {
			return false;
		}
	}

	public static boolean isColumn(Field field) {
		return field.isAnnotationPresent(Column.class);
	}

	public static boolean allowUpdate(Class<?> clazz, String fieldName) {
		try {
			return isColumn(clazz, fieldName)
					&& clazz.getDeclaredField(fieldName).getAnnotation(Column.class).allowUpdate();
		} catch (NoSuchFieldException | SecurityException e) {
			return false;
		}
	}

	public static String getColumnName(Object obj, Field field) {
		return getColumnName(obj.getClass(), field);
	}

	public static String getColumnName(Class<?> clazz, Field field) {
		if (!isColumn(field))
			throw new RuntimeException(field.getName() + " is not a column");
		String columnName = field.getAnnotation(Column.class).value();
		return columnName.isEmpty() ? columnName = StringUtils.camelCaseToUnderline(field.getName()) : columnName;
	}

	public static String getColumnName(Class<?> clazz, String fieldName) {
		Field field = ClassUtils.getDeclaredField(clazz, fieldName);
		return getColumnName(clazz, field);
	}

	public static boolean isForeignKey(Class<?> clazz, String fieldName) {
		if (isColumn(clazz, fieldName)) {
			Field field = ClassUtils.getDeclaredField(clazz, fieldName);
			if (field.getAnnotation(Column.class).isForeignKey())
				return true;
		}
		return false;
	}

	public static String getTableName(Object obj) {
		return getTableName(obj.getClass());
	}

	public static String getTableName(Class<?> clazz) {
		if (!clazz.isAnnotationPresent(Table.class))
			return null;
		String tableName = clazz.getAnnotation(Table.class).value();
		if (StringUtils.isEmpty(tableName))
			return StringUtils.camelCaseToUnderline(clazz.getSimpleName());
		return tableName;
	}

}
