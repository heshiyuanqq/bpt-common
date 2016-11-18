package com.cmri.bpt.common.jdbc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cmri.bpt.common.base.IStringFilter;
import com.cmri.bpt.common.util.DateUtil;
import com.cmri.bpt.common.util.StrUtil;

/**
 * 
 * @author Hu Changwei
 * @version 1.0
 */
public abstract class SqlBuilder {
	public static class BuildingException extends RuntimeException {
		private static final long serialVersionUID = 796828287529766454L;

		public BuildingException() {
			super();
		}

		public BuildingException(String message) {
			super(message);
		}

		public BuildingException(String message, Throwable cause) {
			super(message, cause);
		}

		public BuildingException(Throwable cause) {
			super(cause);
		}
	}

	// 默认占位符
	public static final String JdbcParamPlaceHolder = "?";

	// 默认占位符制造类
	private static class OgnlPlaceHolderMaker implements IPlaceHolderMaker {
		private final static String prefix = "#{";
		private final static String suffix = "}";

		@Override
		public String makePlaceHolder(String orginalStr) {
			return prefix + orginalStr + suffix;
		}

		@Override
		public boolean isPlaceHolder(String valueStr) {
			if (!StrUtil.hasText(valueStr)) {
				return false;
			}
			return valueStr.equals(JdbcParamPlaceHolder) || valueStr.startsWith(prefix) && valueStr.endsWith(suffix);
		}
	}

	public static final IPlaceHolderMaker DefaultPlaceHolderMaker = new OgnlPlaceHolderMaker();

	//
	protected IPlaceHolderMaker placeHolderMaker = SqlBuilder.DefaultPlaceHolderMaker;

	public void setPlaceHolderMaker(IPlaceHolderMaker placeHolderMaker) {
		this.placeHolderMaker = placeHolderMaker;
	}

	protected boolean isPlaceHolder(Object value) {
		if (value instanceof String) {
			return this.placeHolderMaker.isPlaceHolder((String) value);
		}
		return false;
	}

	@SuppressWarnings("rawtypes")
	public static String toValueStr(Object value) {
		if (value == null) {
			return StrUtil.NullStr;
		}
		if (value instanceof CharSequence) {
			return strVal(((CharSequence) value).toString());
		}
		if (value instanceof java.util.Date) {
			return strVal(DateUtil.toStdDateTimeXStr((Date) value));
		}
		if (value instanceof Character) {
			return strVal(((Character) value).toString());
		}
		if (value instanceof Enum) {
			return strVal(((Enum) value).name());
		}
		return value.toString();
	}

	// 常量字符串
	public static final String ParenthesesLeft = "(";
	public static final String ParenthesesRight = ")";
	// 常量字符串-排序
	public static final String OrderAsc = "ASC";
	public static final String OrderDesc = "DESC";

	// 对条目加()
	public static final IStringFilter WrapItemStrFilter = new IStringFilter() {
		public String filter(String original) {
			if (original == null) {
				return Boolean.TRUE.toString();
			}
			return ParenthesesLeft + original + ParenthesesRight;
		}
	};
	// 转义sql语句中字符串值
	public static final IStringFilter ValueStrFilter = new IStringFilter() {
		public String filter(String original) {
			if (original == null) {
				return null;
			}
			return original.replaceAll("'", "''");
		}
	};
	// 转义sql语句中like字符串值
	public static final IStringFilter ValueLikeStrFilter = new IStringFilter() {
		public String filter(String original) {
			if (original == null) {
				return null;
			}
			String str = original;
			str = str.replaceAll("'", "''");
			str = str.replaceAll("\\[", "\\[\\[\\]");
			str = str.replaceAll("%", "[%]");
			str = str.replaceAll("_", "[_]");
			return str;
		}
	};

	// 单引并转义字符串值
	public static String strVal(String val) {
		return val == null ? null : "'" + ValueStrFilter.filter(val) + "'";
	}

	// 单引并转义like字符串值
	protected static String likeStrVal(String val, LikeType likeType) {
		if (val == null) {
			return null;
		}
		if (likeType == null) {
			likeType = LikeType.Default;
		}
		if (likeType == LikeType.Center) {
			return "'%" + ValueLikeStrFilter.filter(val) + "%'";
		} else if (likeType == LikeType.Left) {
			return "'" + ValueLikeStrFilter.filter(val) + "%'";
		} else if (likeType == LikeType.Right) {
			return "'%" + ValueLikeStrFilter.filter(val) + "'";
		} else {
			return "'%" + ValueLikeStrFilter.filter(val) + "%'";
		}
	}

	public static String likeStrVal(String val) {
		return likeStrVal(val, null);
	}

	public static String leftLikeStrVal(String val) {
		return likeStrVal(val, LikeType.Left);
	}

	public static String rightLikeStrVal(String val) {
		return likeStrVal(val, LikeType.Right);
	}

	// 用 AND 连接字符串
	public static String AND(String... andItemStrs) throws BuildingException {
		int totalParams = andItemStrs.length;
		if (totalParams < 1) {
			throw new BuildingException("缺少参数.");
		}
		return StrUtil.join(andItemStrs, " AND ");
	}

	// 用 OR 连接字符串
	public static String OR(String... orItemStrs) throws BuildingException {
		int totalParams = orItemStrs.length;
		if (totalParams < 1) {
			throw new BuildingException("缺少参数.");
		}
		return WrapItemStrFilter.filter(StrUtil.join(orItemStrs, " OR "));
	}

	//
	protected List<String> prependList = new ArrayList<String>();
	//
	protected List<String> appendList = new ArrayList<String>();
	//
	protected String tableName;
	//
	protected boolean isFieldState = false;
	//
	protected List<String> whereList = new ArrayList<String>();

	public SqlBuilder where(String... whereStrs) {
		this.isFieldState = false;
		for (String whereStr : whereStrs) {
			whereList.add(whereStr);
		}
		return this;
	}

	protected String getWhereStr() {
		return StrUtil.join(this.whereList, "\n   AND ");
	}

	public SqlBuilder prepend(String... strs) {
		this.isFieldState = false;
		for (String str : strs) {
			this.prependList.add(str);
		}
		return this;
	}

	public SqlBuilder prependNone() {
		this.isFieldState = false;
		this.prependList.clear();
		return this;
	}

	public SqlBuilder append(String... strs) {
		this.isFieldState = false;
		for (String str : strs) {
			this.appendList.add(str);
		}
		return this;
	}

	public SqlBuilder appendNone() {
		this.isFieldState = false;
		this.appendList.clear();
		return this;
	}

	//
	public abstract String toSQL() throws Exception;
}
