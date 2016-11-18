package com.cmri.bpt.web.page;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;

/**
 * @description Page
 * @class Page
 * @author billz
 * @data 2014-8-25
 */
public class Page<T> {

	private int pageNo = 1;// 页码，默认是第一页
	private int pageSize = 10;// 每页显示的记录数，默认是15
	private long totalRecord;// 总记录数
	private List<T> results;// 对应的当前页记录
	private Map<String, Object> params = new HashMap<String, Object>();// 其他的参数我们把它分装成一个Map对象
	private long realTotalRecord;//用于存放地图的总条数的记录
	
	
	public Page() {
	}

	/**
	 * 分页时使用：获取分页参数，和查询条件
 	 * @param request
	 */
	public Page(HttpServletRequest request) {
		// start 分页参数
		pageNo = StringUtils.isEmpty(request.getParameter("current")) ? 1 : new Integer(request.getParameter("current").toString());
		pageSize = StringUtils.isEmpty(request.getParameter("rowCount")) ? 10 : new Integer(request.getParameter("rowCount").toString());
		// end 
		//
		// start 查询条件参数
		Enumeration<String> paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String paramName = paramNames.nextElement();
			String[] paramValues = request.getParameterValues(paramName);
			if (paramValues.length == 1) {
				String paramValue = paramValues[0];
				if (paramValue.length() != 0) {
					params.put(paramName, paramValue);
				}
			}
		}
		// end
	}

	
	public long getRealTotalRecord() {
		return realTotalRecord;
	}

	public void setRealTotalRecord(long realTotalRecord) {
		this.realTotalRecord = realTotalRecord;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public long getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(long totalRecord) {
		this.totalRecord = totalRecord;
	}

	

	public List<T> getResults() {
		return results;
	}

	public void setResults(List<T> results) {
		this.results = results;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	@Override
	public String toString() {
		Map<String, Object> menuMap = new HashMap<String, Object>();
		menuMap.put("total", totalRecord);
		menuMap.put("rows", results);
		menuMap.put("current", pageNo);
		menuMap.put("rowCount", pageSize);
		menuMap.put("realTotalRecord", realTotalRecord);
		return JSON.toJSONString(menuMap);
	}
}
