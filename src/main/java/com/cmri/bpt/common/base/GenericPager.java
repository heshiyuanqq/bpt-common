package com.cmri.bpt.common.base;

import com.cmri.bpt.common.util.NumUtil;

/**
 * 分页调整基础类
 * 
 * @author koqiui
 * 
 */
public class GenericPager {
	private final Pagination pagination;
	private int pageCount = 0;

	public GenericPager(Pagination pagination) {
		this.pagination = pagination;
		// 纠正分页号
		this.pageCount = NumUtil.ceil(this.pagination.getTotal(), this.pagination.getPageSize());
		int rightPageNumber = NumUtil.narrow(this.pagination.getPageNumber(), 1, this.pageCount);
		this.pagination.setPageNumber(rightPageNumber);
	}

	public int getPageCount() {
		return this.pageCount;
	}

	public Pagination firstPage() {
		if (this.pageCount <= 0) {
			return null;
		}
		this.pagination.setPageNumber(1);
		return this.pagination;
	}

	public Pagination lastPage() {
		if (this.pageCount <= 0) {
			return null;
		}
		this.pagination.setPageNumber(this.pageCount);
		return this.pagination;
	}

	public Pagination nextPage() {
		if (this.isLastPage()) {
			return null;
		}
		this.pagination.setPageNumber(this.pagination.getPageNumber() + 1);
		return this.pagination;
	}

	public Pagination prevPage() {
		if (this.isFirstPage()) {
			return null;
		}
		this.pagination.setPageNumber(this.pagination.getPageNumber() - 1);
		return this.pagination;
	}

	private boolean isFirstPage() {
		return this.pageCount <= 0 || pagination.getPageNumber() == 1;
	}

	private boolean isLastPage() {
		return this.pageCount <= 0 || pagination.getPageNumber() == this.pageCount;
	}
}
