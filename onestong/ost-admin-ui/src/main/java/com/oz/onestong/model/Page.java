package com.oz.onestong.model;

import java.util.Map;

public class Page {
	
	/**
	 * 页面里面的其他参数
	 */
	private Map<String, Object> pageAttrs;
	
	public static final String PAGE_EXCUE_POST = "post";
	public static final String PAGE_EXCUE_GET = "get";
	
	private String method = PAGE_EXCUE_POST;
	
	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Map<String, Object> getPageAttrs() {
		return pageAttrs;
	}

	public void setPageAttrs(Map<String, Object> pageAttrs) {
		this.pageAttrs = pageAttrs;
	}
	/**
	 * 总记录数
	 */
	private int totalRecords;
	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}
	
	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	/**
	 * 多少页
	 */
	private int pageCount;
	/**
	 * 当前请求页码
	 */
	private int curPage;
	/**
	 * 下一页
	 */
	private int nextPage;
	/**
	 * 上一页
	 */
	private int priveousPage;
	/**
	 * 每页显示多少条
	 */
	private int perPageSum;
	/**
	 * 当前访问的url
	 */
	private String pageUrl;
	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public int getNextPage() {
		
		return (curPage-1)*perPageSum;
	}

	public String getPageUrl() {
		return pageUrl;
	}

	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}

	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}

	public int getPriveousPage() {
		return priveousPage;
	}

	public void setPriveousPage(int priveousPage) {
		this.priveousPage = priveousPage;
	}

	public int getPerPageSum() {
		return perPageSum;
	}

	
	@Override
	public String toString() {
		return "Page [totalRecords=" + totalRecords + ", pageCount="
				+ pageCount + ", curPage=" + curPage + ", nextPage=" + nextPage
				+ ", priveousPage=" + priveousPage + ", perPageSum="
				+ perPageSum + ", pageUrl=" + pageUrl + "]";
	}

	public void setPerPageSum(int perPageSum) {
		this.perPageSum = perPageSum;
	}

}
