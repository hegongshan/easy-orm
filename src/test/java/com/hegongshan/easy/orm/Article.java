package com.hegongshan.easy.orm;

import com.hegongshan.easy.orm.annotation.Column;
import com.hegongshan.easy.orm.annotation.Id;
import com.hegongshan.easy.orm.annotation.Order;
import com.hegongshan.easy.orm.annotation.Table;

@Table
public class Article implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Column
	private String articleTitle;
	@Column(orderBy=true,order=Order.ASC,priority=2)
	private java.util.Date gmtModify;
	@Column
	private Integer articleClicks;
	@Id
	private Integer articleId;
	@Column
	private String articleContent;
	@Column
	private Integer articleLikes;
	@Column
	private String articleSummary;
	@Column
	private Integer articleCommentNum;
	@Column(allowUpdate=false,orderBy=true,order=Order.DESC,priority=1)
	private java.util.Date gmtCreate;
	@Column(isForeignKey=true)
	private Integer categoryId;
	//private Category category;

	public String getArticleTitle () {
		return articleTitle;
	}

	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}
	public java.util.Date getGmtModify () {
		return gmtModify;
	}

	public void setGmtModify(java.util.Date gmtModify) {
		this.gmtModify = gmtModify;
	}
	public Integer getArticleClicks () {
		return articleClicks;
	}

	public void setArticleClicks(Integer articleClicks) {
		this.articleClicks = articleClicks;
	}
	public Integer getArticleId () {
		return articleId;
	}

	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}
	public String getArticleContent () {
		return articleContent;
	}

	public void setArticleContent(String articleContent) {
		this.articleContent = articleContent;
	}
	public Integer getArticleLikes () {
		return articleLikes;
	}

	public void setArticleLikes(Integer articleLikes) {
		this.articleLikes = articleLikes;
	}
	public String getArticleSummary () {
		return articleSummary;
	}

	public void setArticleSummary(String articleSummary) {
		this.articleSummary = articleSummary;
	}
	public Integer getArticleCommentNum () {
		return articleCommentNum;
	}

	public void setArticleCommentNum(Integer articleCommentNum) {
		this.articleCommentNum = articleCommentNum;
	}
	public java.util.Date getGmtCreate () {
		return gmtCreate;
	}

	public void setGmtCreate(java.util.Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
	public Integer getCategoryId () {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	
	

	@Override
	public String toString() {
		return "Article [articleTitle=" + articleTitle + ", gmtModify=" + gmtModify + ", articleClicks=" + articleClicks
				+ ", articleId=" + articleId + ", articleContent=" + articleContent + ", articleLikes=" + articleLikes
				+ ", articleSummary=" + articleSummary + ", articleCommentNum=" + articleCommentNum + ", gmtCreate="
				+ gmtCreate + ", category=" + categoryId + "]";
	}
}