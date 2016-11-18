package com.cmri.bpt.common.base;

/**
 * 分页基本信息
 * 
 * @author koqiui
 * 
 */
public class Pagination {
	private int total = 0;
	private int pageSize = 10;
	private int pageNumber = 1;
	//
	private Range<Integer> pageIndexRange = Range.newOne(-1, -1);

	private void adjustPageIndexRange() {
		int fromIndex = -1;
		int toIndex = -1;
		if (total > 0) {
			fromIndex = (pageNumber - 1) * pageSize;
			toIndex = Math.min(total - 1, pageNumber * pageSize - 1);
		}
		pageIndexRange.setFrom(fromIndex);
		pageIndexRange.setTo(toIndex);
	}

	//

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
		//
		this.adjustPageIndexRange();
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
		//
		this.adjustPageIndexRange();
	}

	public Range<Integer> getPageIndexRange() {
		return pageIndexRange;
	}

	public void setPageIndexRange(Range<Integer> pageIndexRange) {
		this.pageIndexRange = pageIndexRange;
	}
}
