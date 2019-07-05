package com.xsdzq.mm.model;

public class Pagination {
	private int pageNumber;
	private int pageSize;

	private int totalItems;

	public Pagination() {
		super();
	}

	public Pagination(int pageNumber, int pageSize) {
		super();
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
	}

	public Pagination(int pageNumber, int pageSize, int totalItems) {
		super();
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		this.totalItems = totalItems;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalItems() {
		return totalItems;
	}

	public void setTotalItems(int totalItems) {
		this.totalItems = totalItems;
	}

	@Override
	public String toString() {
		return "Pagination [pageNumber=" + pageNumber + ", pageSize=" + pageSize + ", totalItems=" + totalItems + "]";
	}

}
