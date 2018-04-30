package com.hegongshan.easy.orm.core;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.sql.DataSource;

import com.hegongshan.easy.orm.Constants;
import com.hegongshan.easy.orm.annotation.Column;
import com.hegongshan.easy.orm.annotation.GeneratedPolicy;
import com.hegongshan.easy.orm.annotation.Id;
import com.hegongshan.easy.orm.bean.Page;
import com.hegongshan.easy.orm.bean.PageResult;
import com.hegongshan.easy.orm.ds.DataSourceFactory;
import com.hegongshan.easy.orm.exception.ParameterMismatchExcetion;
import com.hegongshan.easy.orm.exception.ParameterNotEnoughException;
import com.hegongshan.easy.orm.exception.TransactionException;
import com.hegongshan.easy.orm.handler.ResultSetHandler;
import com.hegongshan.easy.orm.sql.SqlGenerator;
import com.hegongshan.easy.orm.util.ClassUtils;
import com.hegongshan.easy.orm.util.JdbcUtils;

public class DAO<T> {
	private DataSource ds = DataSourceFactory.getDataSource();
	private Connection conn;
	private Class<T> mappedClass;
	
	@SuppressWarnings("unchecked")
	protected DAO() {
		ParameterizedType pt = (ParameterizedType) getClass().getGenericSuperclass();
		Type genericType = pt.getActualTypeArguments()[0];
		mappedClass = (Class<T>) genericType;
	}
	
	public DAO(Class<T> mappedClass) {
		this.mappedClass = mappedClass;
	}
	/**
	 * 通过联合主键删除
	 * @param t 需要删除的对象，必须设置所有的主键值
	 * @return 影响的行数
	 * @throws SQLException
	 */
	public int delete(T t) throws SQLException {
		String sql = SqlGenerator.generateDeleteSql(mappedClass);
		
		Object[] params = EntityTable.getIdValues(t).toArray();
		
		return executeUpdate(sql,params);
	}
	
	/**
	 * @param ids 主键顺序必须和实体类中id的顺序一致
	 * @return
	 * @throws SQLException
	 */
	public int deleteById(Object ... ids) throws SQLException {
		if(ids == null || ids.length == 0)
			throw new ParameterNotEnoughException("parameters are not enough");
		String sql = SqlGenerator.generateDeleteSql(mappedClass);
		
		return executeUpdate(sql,ids);
	}
	
	public int insert(T t) throws SQLException {
		String sql = SqlGenerator.generateInsertSql(t);

		Object[] params = getSqlParameterValues(t,DML.INSERT);
		
		return executeUpdate(sql,params);
	}

	public T queryOne(String sql,Object ... params) throws SQLException {
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		T t = ResultSetHandler.toBean(mappedClass,rs);
		JdbcUtils.close(rs,stmt,conn);
		return t;
		
	}
	
	public List<T> queryList(String sql,Object ... params) throws SQLException {
		Connection conn = getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		List<T> list = ResultSetHandler.toList(mappedClass,rs);
		JdbcUtils.close(rs, pstmt,conn);
		return list;
	}
	
	public T queryById(Object ... ids) throws SQLException {
		String sql = SqlGenerator.generateSelectSql(mappedClass);
		return queryOne(sql,ids);
	}
	//todo
	public PageResult<T> queryByPage(Page page) throws SQLException {
		String sql = SqlGenerator.generateSelectSqlForPage(mappedClass,Constants.DATABASE_PROVIDER);
		PageResult<T> result = new PageResult<T>();
		result.setList(queryList(sql,page.getStartRow(),page.getPageSize()));
		result.setCurrentPage(page.getCurrentPage());
		result.setTotalPage(count());
		return result;
	}
	
	public List<T> queryAll() throws SQLException {
		String sql = SqlGenerator.generateSelectSql(mappedClass,false);
		return queryList(sql);
	}
	
	public int count() throws SQLException {
		String sql = SqlGenerator.generateSelectSqlForCount(mappedClass);
		Connection conn = getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		int count = ResultSetHandler.toInteger(rs);
		JdbcUtils.close(rs,pstmt,conn);
		return count;
	}

	public int update(T t) throws SQLException {
		String sql = SqlGenerator.generateUpdateSql(t);
		
		Object[] params = getSqlParameterValues(t,DML.UPDATE);
		
		return executeUpdate(sql, params);
	}
	
	public int executeUpdate(String sql,Object ... params) throws SQLException {
		Connection conn = getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		fillParameter(pstmt, params);
		int result = pstmt.executeUpdate();
		JdbcUtils.close(pstmt,conn);
		return result;
	}
	
	private Object[] getSqlParameterValues(T t,DML operation) {
		Field[] fields = t.getClass().getDeclaredFields();
		List<Object> fieldValueList = new ArrayList<Object>(fields.length);
		for (Field field : fields) {
			if(ClassUtils.isFinal(field))
				continue;
			if(operation == DML.UPDATE) {
				//若更新，判断列是否允许更新，跳过不可更新的列
				if(EntityTable.isColumn(field)&&!field.getAnnotation(Column.class).allowUpdate()) {
					continue;
				}
			} else if(operation == DML.INSERT) {
				//若插入，判断主键的生成策略，若为自动生成，跳过主键
				if(EntityTable.isId(field)&& field.getAnnotation(Id.class).policy() == GeneratedPolicy.IDENTITY)
					continue;
			}
			Object fieldValue = ClassUtils.getField(t, field);
			if(Objects.isNull(fieldValue))
				continue;
			fieldValueList.add(fieldValue);
		}
		return fieldValueList.toArray();
	}
	
	private void fillParameter(PreparedStatement pstmt, Object ... params) throws SQLException {
		ParameterMetaData pmd = pstmt.getParameterMetaData();
		int parameterCount = pmd.getParameterCount();
		if (parameterCount != params.length) {
			throw new ParameterMismatchExcetion("The length of params mismatch the number of sql parameter");
		}
		for (int i = 0; i < parameterCount; i++) {
			pstmt.setObject(i + 1, params[i]);
		}
	}
	
	private synchronized Connection getConnection() throws SQLException {
		if(conn == null) {
			conn = ds.getConnection();
		}
		return conn;
	}
	
	public void beginTransaction() {
		try {
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			throw new TransactionException("begin transaction fail",e);
		}
	}
	
	public void commit() {
		try {
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {}
		}
	}
}
