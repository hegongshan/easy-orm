package com.hegongshan.easy.orm.handler;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hegongshan.easy.orm.core.EntityTable;
import com.hegongshan.easy.orm.util.ClassUtils;

public class ResultSetHandler {
	public static <T> T toBean(Class<T> mappedClass,ResultSet rs) throws SQLException {
		if(rs.isBeforeFirst())
			rs.next();
		Field[] fields = mappedClass.getDeclaredFields();
		T t = null;
		try {
			t = mappedClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		Object fieldValue = null;
		String columnName = null;
		for(Field field : fields) {
			if(ClassUtils.isFinal(field))
				continue;
			if(EntityTable.isId(field))
				columnName = EntityTable.getIdName(mappedClass, field);
			if(EntityTable.isColumn(field))
				columnName = EntityTable.getColumnName(mappedClass, field);
			fieldValue = rs.getObject(columnName);
			ClassUtils.setField(t, field, fieldValue);
		}
		return t;
	}
	
	public static <T> List<T> toList(Class<T> mappedClass,ResultSet rs) throws SQLException {
		List<T> list = new ArrayList<T>();
		while(rs.next()) {
			list.add(toBean(mappedClass,rs));
		}
		return list;
	}
	
	public static <T> Integer toInteger(ResultSet rs) throws SQLException {
		int count = 0;
		if(rs.next()) 
			count = rs.getInt(1);
		return count;
	}
}
