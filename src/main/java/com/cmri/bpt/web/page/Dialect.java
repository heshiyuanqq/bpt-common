package com.cmri.bpt.web.page;

/**
 * 类似hibernate的Dialect,但只精简出分页部分
 * 
 * @author billz
 */
public abstract class Dialect {

	public abstract String buildPageSql(Page<?> page, StringBuffer sqlBuffer);

	/**
	 * 根据page对象获取对应的分页查询Sql语句，这里只做了两种数据库类型，Mysql和Oracle 其它的数据库都 没有进行分页
	 * 
	 * @param page
	 *            分页对象
	 * @param sql
	 *            原sql语句
	 * @return
	 */
	public static String getPageSql(String databaseType, Page<?> page,
			String sql) {
		StringBuffer sqlBuffer = new StringBuffer(sql);
		Dialect dt = null;
		if ("mysql".equalsIgnoreCase(databaseType)) {
			dt = new MySql5Dialect();
		} else if ("oracle".equalsIgnoreCase(databaseType)) {
			dt = new OracleDialect();
		}
		return dt.buildPageSql(page, sqlBuffer);
	}

}
