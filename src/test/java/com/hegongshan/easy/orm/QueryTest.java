package com.hegongshan.easy.orm;

import java.sql.SQLException;
import java.time.Instant;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.hegongshan.easy.orm.bean.Page;
import com.hegongshan.easy.orm.bean.PageResult;
import com.hegongshan.easy.orm.core.DAO;

public class QueryTest extends DAO<Article>{

	//private BasicDataSource ds;
	private final DAO<Article> dao = new DAO<Article>(Article.class);
	
	/*@Before
	public void before() {
		ds = new BasicDataSource();
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setUrl("jdbc:mysql://localhost:3306/db_blog");
		ds.setUsername("root");
		ds.setPassword("mysqladmin");
		ds.setInitialSize(2);
		ds.setMaxTotal(12);
	}*/
	
	@Test
	public void testInsert() throws SQLException {
		Article article;
		for(int i = 0;i<50;i++) {
			article = new Article();
			article.setArticleClicks(10);
			article.setArticleCommentNum(10);
			article.setArticleTitle("This is a test row");
			article.setArticleContent("article content is not null");
			article.setArticleLikes(100);
			Date date = Date.from(Instant.now());
			article.setGmtCreate(date);
			article.setGmtModify(date);
			article.setArticleSummary("This is the summary of article");
			article.setCategoryId(1);
			int record = dao.insert(article);
			System.out.println(record+"-");
		}
	}
	
	@Test
	public void testUpdate() throws SQLException {
		Article article = new Article();
		Date date = Date.from(Instant.now());
		article.setGmtCreate(date);
		article.setGmtModify(date);
		//article.setCategoryId(2);
		article.setArticleId(18);
		int record = dao.update(article);
		System.out.println(record);
	}
	
	@Test
	public void testdeleteById() throws SQLException {
		beginTransaction();
		int record = deleteById(19);
		Article article = new Article();
		Date date = Date.from(Instant.now());
		article.setGmtCreate(date);
		article.setGmtModify(date);
		//article.setCategoryId(2);
		article.setArticleId(18);
		int record2 = dao.update(article);
		
		commit();
		System.out.println(record);
		System.out.println(record2);
	}
	
	@Test
	public void testqueryById() throws SQLException {
		Article article = dao.queryById(27);
		System.out.println(article);
	}
	
	@Test
	public void testqueryByPage() throws SQLException {
		Page page = new Page();
		page.setCurrentPage(1);
		page.setPageSize(10);
		PageResult<Article> pr = queryByPage(page);
		System.out.println(pr.getList());
	}
	
	@Test
	public void testquery() throws SQLException {
		List<Article> article = queryAll();
		System.out.println(article);
	}
	
	@Test
	public void testCount() throws SQLException {
		int article = count();
		System.out.println(article);
	}
	/*
	@After
	public void close() throws SQLException {
		ds.close();
	}*/
}