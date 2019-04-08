package com.store.domain;

import java.util.List;

public class PageModel<T> {
	// 基本属性
	private int currentPageNum;// 当前页数
	private int pageSize = 10;// 每页显示的内容条数
	private int totalRecords;// 总共的记录条数
	private int totalPageNum;// 总共的页数,计算出来的
	private int startIndex;// 每页开始的索引
	private int prePageNum;// 上一页
	private int nextPageNum;// 下一页
	private List<T> list;// 当前的页的内容

	// 扩展属性
	// 一共显示9个页码按钮
	private int startPage;// 开始页码
	private int endPage;// 结束页码

	private String url;

	public PageModel(int currentPageNum, int pageSize, int totalRecords) {
		this.currentPageNum = currentPageNum;
		this.pageSize = pageSize;
		this.totalRecords = totalRecords;

		totalPageNum = totalRecords % pageSize == 0 ? (totalRecords / pageSize) : (totalRecords / pageSize + 1);
		startIndex = (currentPageNum - 1) * pageSize;
		startPage = currentPageNum - pageSize / 2; // 5
		endPage = currentPageNum + pageSize / 2; // 13
		// 看看总页数够不够9页
		if (totalPageNum > pageSize) {
			// 超过了9页
			if (startPage < 1) {
				startPage = 1;
				endPage = startPage + pageSize - 1;
			}
			if (endPage > totalPageNum) {
				endPage = totalPageNum;
				startPage = endPage - (pageSize - 1);
			}
		} else {
			// 不够9页
			startPage = 1;
			endPage = totalPageNum;
		}
	}

	public int getCurrentPageNum() {
		return currentPageNum;
	}

	public void setCurrentPageNum(int currentPageNum) {
		this.currentPageNum = currentPageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	public int getTotalPageNum() {
		return totalPageNum;
	}

	public void setTotalPageNum(int totalPageNum) {
		this.totalPageNum = totalPageNum;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public int getPrePageNum() {
		return currentPageNum-1;
	}

	public void setPrePageNum(int prePageNum) {
		this.prePageNum = prePageNum;
	}

	public int getNextPageNum() {
		return currentPageNum+1;
	}

	public void setNextPageNum(int nextPageNum) {
		this.nextPageNum = nextPageNum;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public PageModel() {
	}

}
