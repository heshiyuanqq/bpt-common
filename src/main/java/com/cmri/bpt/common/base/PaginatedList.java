package com.cmri.bpt.common.base;

import java.util.List;

public class PaginatedList<TData> {
	private Pagination pagination = new Pagination();
	private List<TData> rows;

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public List<TData> getRows() {
		return rows;
	}

	public void setRows(List<TData> rows) {
		this.rows = rows;
	}
}
