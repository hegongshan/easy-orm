package com.hegongshan.easy.orm;

import com.hegongshan.easy.orm.annotation.Column;
import com.hegongshan.easy.orm.annotation.Id;

public class Category implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Column
	private java.util.Date gmtModify;
	@Column
	private String typeName;
	@Column
	private String description;
	@Column(allowUpdate=false)
	private java.util.Date gmtCreate;
	@Id
	private Integer categoryId;

	public java.util.Date getGmtModify () {
		return gmtModify;
	}

	public void setGmtModify(java.util.Date gmtModify) {
		this.gmtModify = gmtModify;
	}
	public String getTypeName () {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getDescription () {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
		return "Category [gmtModify=" + gmtModify + ", typeName=" + typeName + ", description=" + description
				+ ", gmtCreate=" + gmtCreate + ", categoryId=" + categoryId + "]";
	}
	
}