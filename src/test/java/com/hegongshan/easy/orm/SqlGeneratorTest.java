package com.hegongshan.easy.orm;

import java.time.Instant;
import java.util.Date;

import org.junit.Test;

import com.hegongshan.easy.orm.core.DatabaseProvider;
import com.hegongshan.easy.orm.sql.SqlGenerator;

public class SqlGeneratorTest {
	
	/**
	 * 1.测试不带条件、排序、分组和分页的select sql,预期sql：select * from article
	 */
	@Test
	public void testSimpleSelectSql() {
		System.out.println(SqlGenerator.generateSelectSql(Article.class,false,false));
	}
	
	/**
	 * 2.测试不带条件、分组和分页，但排序的select sql,
	 * 预期sql：select * from article order by gmt_create
	 */
	@Test
	public void testSelectSqlWithOrder() {
		System.out.println(SqlGenerator.generateSelectSql(Article.class,false));
	}
	
	/**
	 * 3.测试不带分组和分页，但带条件和排序的select sql,
	 * 预期sql：select * from article where article_id = ? order by gmt_create
	 */
	@Test
	public void testSelectSqlWithClauseAndOrder() {
		System.out.println(SqlGenerator.generateSelectSql(Article.class));
	}
	
	/**
	 * 4.测试简单的查询表中记录数的select sql,
	 * 预期sql：select count(*) from article
	 */
	@Test
	public void testSelectSqlForCount() {
		System.out.println(SqlGenerator.generateSelectSqlForCount(Article.class));
	}
	
	/**
	 * 5.测试带条件的查询表中记录数的select sql,一般应用于实现多对多联系的中间表，暂时没有实现，todo
	 * 预期sql：select count(*) from article where xx = ?
	 */
	@Test
	public void testSelectSqlForCountWithClause() {
		System.out.println(SqlGenerator.generateSelectSqlForCount(Article.class));
	}
	
	/**
	 * 5.测试带条件，排序并分页的select sql,
	 * 预期sql：select * from article where article_id = ? order by gmt_create limit ? , ?
	 */
	@Test
	public void testSelectSqlWithClauseAndOrderAndPage() {
		System.out.println(SqlGenerator.generateSelectSqlForPage(Article.class,DatabaseProvider.MYSQL));
	}
	
	@Test
	public void testInsertSql() {
		Article a = new Article();
		a.setArticleClicks(0);
		a.setArticleCommentNum(0);
		a.setArticleContent(" ihao");
		a.setArticleSummary("1234");
		a.setArticleTitle("???");
		Date date = Date.from(Instant.now());
		a.setGmtCreate(date);
		a.setGmtModify(date);
		a.setCategoryId(1);
		a.setArticleLikes(0);
		System.out.println(SqlGenerator.generateInsertSql(a));
	}
	
	@Test
	public void testdeleteSql() {
		System.out.println(SqlGenerator.generateDeleteSql(Article.class,false));
	}
	
	@Test
	public void testdeleteSqlWithClause() {
		System.out.println(SqlGenerator.generateDeleteSql(Article.class));
	}
	
	@Test
	public void testUpdateSql() {
		Article a = new Article();
		a.setArticleClicks(0);
		a.setArticleCommentNum(0);
		a.setArticleContent(" ihao");
		a.setArticleSummary("1234");
		a.setArticleTitle("???");
		Date date = Date.from(Instant.now());
		a.setGmtCreate(date);
		a.setGmtModify(date);
		a.setCategoryId(1);
		a.setArticleLikes(0);
		System.out.println(SqlGenerator.generateUpdateSql(a));
	}

	
	
	
}
