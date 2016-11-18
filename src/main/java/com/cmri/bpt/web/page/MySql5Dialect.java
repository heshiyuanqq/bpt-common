package com.cmri.bpt.web.page;

import java.util.Map;

/**
 * @description MySql5Dialect
 * @class MySql5Dialect
 * @author billz
 * @data 2014-8-25
 */
public class MySql5Dialect extends Dialect {

	/** 
     * 获取Mysql数据库的分页查询语句 
     * @param page 分页对象 
     * @param sqlBuffer 包含原sql语句的StringBuffer对象 
     * @return Mysql数据库分页语句 
     */ 
	@Override
	public String buildPageSql(Page<?> page, StringBuffer sqlBuffer) {
		//page 中含有sort排序
		 Map<String, Object>  map=page.getParams();
		 for (String key : map.keySet()) {
			 if(key.startsWith("sort[")){
					if(sqlBuffer.toString().toLowerCase().indexOf("order")>0){
						String str=sqlBuffer.substring(0, sqlBuffer.toString().toLowerCase().indexOf("order"));
						sqlBuffer = new StringBuffer(str);
						break;
					}
			 }
		 }
		 for (String key : map.keySet()) {
			 if(key.startsWith("sort[")){
				 //含有排序
				   String name=key.replace("sort[", " ").replace("]", " ");
				   String value=(String)map.get(key);
				   sqlBuffer.append("  order by ");
				   sqlBuffer.append(name);
				   sqlBuffer.append(value);
			 }
		}
		if(page.getPageSize()==-1){
			//all
			return sqlBuffer.toString();
		}else{
			// 计算第一条记录的位置，Mysql中记录的位置是从0开始的。
			int offset = (page.getPageNo()- 1) * page.getPageSize();
			sqlBuffer.append(" limit ").append(offset).append(",")
					.append(page.getPageSize());
			return sqlBuffer.toString();
		}
		
	}

}
