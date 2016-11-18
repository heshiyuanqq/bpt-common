package com.cmri.bpt.common.base;

import java.util.Map;

public class PaginatedFilter {

	private Pagination pagination = new Pagination();
	private Map<String, Object> filters;

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public Map<String, Object> getFilters() {
		return filters;
	}

	public void setFilters(Map<String, Object> filters) {
		this.filters = filters;
	}
}
