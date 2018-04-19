package com.hegongshan.easy.orm.bean;

public class Page {
	private Integer startRow;
	private Integer currentPage;
	private Integer pageSize;

	public Integer getStartRow() {
		startRow = (currentPage - 1) * pageSize;
		return startRow;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

}
