package com.hegongshan.easy.orm.sql;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Set;

import com.hegongshan.easy.orm.annotation.Column;
import com.hegongshan.easy.orm.annotation.GeneratedPolicy;
import com.hegongshan.easy.orm.annotation.Id;
import com.hegongshan.easy.orm.annotation.Order;
import com.hegongshan.easy.orm.bean.ColumnInfo;
import com.hegongshan.easy.orm.core.DatabaseProvider;
import com.hegongshan.easy.orm.core.EntityTable;
import com.hegongshan.easy.orm.exception.SQLGenerateException;
import com.hegongshan.easy.orm.util.ClassUtils;
import com.hegongshan.easy.orm.util.StringUtils;

public class SqlGenerator {
	
	public static String generateDeleteSql(Class<?> clazz) {
		return generateDeleteSql(clazz,true);
	}

	public static String generateDeleteSql(Class<?> clazz,boolean hasClause) {
		StringBuilder sql = new StringBuilder();
		String tableName = EntityTable.getTableName(clazz);
		sql.append("delete from ").append(tableName);
		if(!hasClause)
			return sql.toString();
		sql.append(" where ");
		Set<String> ids = EntityTable.getIdNames(clazz);
		for(String id : ids) {
			sql.append(id).append(" = ? and ");
		}
		sql.delete(sql.lastIndexOf("and "),sql.length());
		return sql.toString();
	}
	
	public static String generateUpdateSql(Object obj) {
		if(obj instanceof Class<?>) {
			throw new SQLGenerateException(obj.getClass().getName()+" is not a instance");
		}
		StringBuilder sql = new StringBuilder();
		String tableName = EntityTable.getTableName(obj);
		sql.append("update ").append(tableName).append(" set ");
		Field[] fields = obj.getClass().getDeclaredFields();
		for (Field field : fields) {
			if (Modifier.isFinal(field.getModifiers()) || ClassUtils.getField(obj, field) == null) {
				continue;
			}
			if(EntityTable.isColumn(field)&&!field.getAnnotation(Column.class).allowUpdate()) {
				continue;
			}
			if(EntityTable.isId(obj, field.getName())) {
				continue;
			}
			sql.append(StringUtils.camelCaseToUnderline(field.getName()))
			   .append(" = ? ,");
		}
		sql.deleteCharAt(sql.lastIndexOf(","));
		Set<String> idNames = EntityTable.getIdNames(obj);
		if (idNames == null || idNames.size() == 0) {
			return sql.toString();
		}
		sql.append("where ");
		for (String id : idNames) {
			sql.append(id).append(" = ? ,");
		}
		sql.deleteCharAt(sql.lastIndexOf(","));
		return sql.toString();
	}
	
	public static String generateInsertSql(Object obj) {
		if(obj instanceof Class<?>) {
			throw new SQLGenerateException(obj.getClass().getName()+" is not a instance");
		}
		StringBuilder sql = new StringBuilder();
		String tableName = EntityTable.getTableName(obj);
		sql.append("insert into ").append(tableName).append(" (");
		Field[] fields = obj.getClass().getDeclaredFields();
		int count = 0;
		for (Field field : fields) {
			if(ClassUtils.isFinal(field)) {
				continue;
			}
			if (ClassUtils.getField(obj, field) == null) {
				continue;
			}
			if(EntityTable.isId(field) && field.getAnnotation(Id.class).policy() != GeneratedPolicy.IDENTITY) {
				sql.append(EntityTable.getIdName(obj, field) + ",");
			}
			sql.append(EntityTable.getColumnName(obj, field) + ",");
			count++;
		}
		if(!sql.toString().contains(","))
			throw new SQLGenerateException(obj.getClass().getName()+" has not id and column.");
		sql.deleteCharAt(sql.lastIndexOf(","));
		sql.append(") values (");
		for (int i = 0;i < count;i++) {
			sql.append("?,");
		}
		if(sql.lastIndexOf(",") != -1) {
			sql.deleteCharAt(sql.lastIndexOf(","));
		}
		sql.append(")");
		return sql.toString();
	}
	
	public static String generateSelectSqlForCount(Class<?> clazz) {
		StringBuilder sql = new StringBuilder("select count(*) from ");
		sql.append(EntityTable.getTableName(clazz));
		return sql.toString();
	}
	
	public static String generateSelectSqlForPage(Class<?> clazz,DatabaseProvider db) {
		return generateSelectSqlForPage(clazz,db,true);
	}
	
	public static String generateSelectSqlForPage(Class<?> clazz,DatabaseProvider db,boolean hasClause) {
		return generateSelectSqlForPage(clazz,db,hasClause,true);
	}
	
	//todo,support only mysql now.
	public static String generateSelectSqlForPage(Class<?> clazz,DatabaseProvider db,boolean hasClause,boolean order) {
		StringBuilder sql = new StringBuilder(generateSelectSql(clazz,hasClause,order));
		if(db == DatabaseProvider.MYSQL)
			sql.append(" limit ?,?");
		else if(db == DatabaseProvider.ORACLE)
			sql.append(" rownum < ?");
		else if(db == DatabaseProvider.SQL_SERVER)
			sql.append(" top < ?");
		return sql.toString();
	}
	
	public static String generateSelectSql(Class<?> clazz) {
		return generateSelectSql(clazz,true,true);
	}
	
	public static String generateSelectSql(Class<?> clazz,boolean hasClause) {
		return generateSelectSql(clazz,hasClause,true);
	}
	
	public static String generateSelectSql(Class<?> clazz,boolean hasClause,boolean order) {
		StringBuilder sql = new StringBuilder();
		String tableName = EntityTable.getTableName(clazz);
		sql.append("select * from ").append(tableName).append(" ");
		//如果有条件
		if(hasClause) {
			Set<String> idNames = EntityTable.getIdNames(clazz);
			if(idNames!=null) {
				sql.append("where ");
				int count = 0;
				for(String idName : idNames) {
					count++;
					if(count == idNames.size())
						sql.append(idName +" = ? ");
					else
						sql.append(idName +" = ? and ");
				}
			}
		}
		//如果需要排序
		if(order) {
			List<ColumnInfo> columns = EntityTable.getOrderColumn(clazz);
			if(columns.size() == 0) {
				return sql.toString();
			}
			sql.append("order by ");
			for(ColumnInfo column : columns) {
				sql.append(column.getValue()).append(" ");
				if(column.getOrder() == Order.DESC) {
					sql.append("desc");
				}
				sql.append(",");
			}
			sql.deleteCharAt(sql.lastIndexOf(","));
		}
		return sql.toString();
	}

}
