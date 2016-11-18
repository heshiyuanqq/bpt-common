package com.cmri.bpt.common.util;

import java.util.List;

import com.cmri.bpt.common.base.IStringFilter;
import com.cmri.bpt.common.helper.FileHelper;

/**
 * 
 * @author Hu Changwei
 * @date 2013-12-13
 * 
 */
public class StrUtil {
	private StrUtil() {
		// prevent from being initialized
	}

	public static final String EmptyStr = "";
	public static final String NullStr = "null";
	public static final String JoinSepChars = ",";
	public static final char SpaceChar = ' ';
	public static final char SlashChar = '/';
	public static final String SlashStr = "/";
	public static final char BackSlashChar = '\\';
	public static final String BackSlashStr = "\\";
	public static final char[] FileSepChars = FileHelper.FILE_SEPERATOR.toCharArray();
	public static final char Hyphen = '-';
	public static final String NewLine = OSUtil.getLineSeparator();
	public static final String CR = "\r";
	public static final String LF = "\n";
	public static final String EOL = CR + LF;

	public static boolean isNullOrBlank(String chkStr) {
		return chkStr == null || chkStr.trim().equals(EmptyStr);
	}

	public static boolean hasText(String chkStr) {
		return chkStr != null && !chkStr.trim().equals(EmptyStr);
	}

	public static boolean hasText(Character chkChar) {
		return chkChar != null && !chkChar.toString().trim().equals(EmptyStr);
	}

	public static boolean isNullStr(String chkStr) {
		return NullStr.equals(chkStr);
	}

	public static String toString(Object object) {
		return object == null ? NullStr : String.valueOf(object);
	}

	public static String nullAs(Object object, String nullAs) {
		return object == null ? nullAs : String.valueOf(object);
	}

	public static String nullAsEmpty(Object object) {
		return object == null ? EmptyStr : String.valueOf(object);
	}

	public static String[] split(String str) {
		return str == null ? null : str.split(JoinSepChars, -1);
	}

	public static <T> String join(Iterable<T> iter) {
		return join(iter, JoinSepChars);
	}

	public static <T> String join(Iterable<T> iter, String sepChars) {
		if (iter == null) {
			return null;
		}
		if (sepChars == null) {
			sepChars = JoinSepChars;
		}
		StringBuilder sb = new StringBuilder();
		boolean isFirst = true;
		for (T item : iter) {
			if (isFirst) {
				isFirst = false;
			} else {
				sb.append(sepChars);
			}
			sb.append(item);
		}
		return sb.toString();
	}

	public static <T> String join(List<T> list) {
		return join(list, null);
	}

	public static <T> String join(List<T> list, String delimeter) {
		if (list == null) {
			return NullStr;
		}
		if (delimeter == null) {
			delimeter = ",";
		}
		StringBuilder sb = new StringBuilder();
		int len = list.size();
		for (int i = 0; i < len; i++) {
			if (i > 0) {
				sb.append(delimeter);
			}
			T item = list.get(i);
			sb.append(item == null ? NullStr : item.toString());
		}
		return sb.toString();
	}

	public static <T> String join(List<T> list, String delimeter, IStringFilter itemStrFilter) {
		if (list == null) {
			return itemStrFilter.filter(null);
		}
		if (delimeter == null) {
			delimeter = ",";
		}
		StringBuilder sb = new StringBuilder();
		int len = list.size();
		for (int i = 0; i < len; i++) {
			if (i > 0) {
				sb.append(delimeter);
			}
			T item = list.get(i);
			sb.append(item == null ? itemStrFilter.filter(null) : itemStrFilter.filter(item.toString()));
		}
		return sb.toString();
	}

	public static <T> String join(T[] list) {
		return join(list, null);
	}

	public static <T> String join(T[] list, String delimeter) {
		if (list == null) {
			return NullStr;
		}
		if (delimeter == null) {
			delimeter = ",";
		}
		StringBuilder sb = new StringBuilder();
		int len = list.length;
		for (int i = 0; i < len; i++) {
			if (i > 0) {
				sb.append(delimeter);
			}
			T item = list[i];
			sb.append(item == null ? NullStr : item.toString());
		}
		return sb.toString();
	}

	public static <T> String join(T[] list, String delimeter, IStringFilter itemStrFilter) {
		if (list == null) {
			return itemStrFilter.filter(null);
		}
		if (delimeter == null) {
			delimeter = ",";
		}
		StringBuilder sb = new StringBuilder();
		int len = list.length;
		for (int i = 0; i < len; i++) {
			if (i > 0) {
				sb.append(delimeter);
			}
			T item = list[i];
			sb.append(item == null ? itemStrFilter.filter(null) : itemStrFilter.filter(item.toString()));
		}
		return sb.toString();
	}

	public static String leftPadding(String str, int length) {
		return leftPadding(str, length, SpaceChar);
	}

	public static String leftPadding(String str, int length, char paddingChar) {
		int strLen = str.length();
		if (strLen >= length) {
			return str;
		}
		StringBuilder sb = new StringBuilder();
		for (int i = strLen; i < length; i++) {
			sb.append(paddingChar);
		}
		return sb.toString() + str;
	}

	public static String rightPadding(String str, int length) {
		return rightPadding(str, length, SpaceChar);
	}

	public static String rightPadding(String str, int length, char paddingChar) {
		int strLen = str.length();
		if (strLen >= length) {
			return str;
		}
		StringBuilder sb = new StringBuilder();
		for (int i = strLen; i < length; i++) {
			sb.append(paddingChar);
		}
		return str + sb.toString();
	}

	public static String left(String str, int length) {
		int len = str.length();
		return len <= length ? str : str.substring(0, length);
	}

	public static String right(String str, int length) {
		int len = str.length();
		return len <= length ? str : str.substring(len - length);
	}

	public static String seperate(String str, int unitLen) {
		return seperate(str, unitLen, SpaceChar);
	}

	public static String seperate(String str, int unitLen, char sepChar) {
		if (unitLen <= 0) {
			return str;
		}
		int len = str.length();
		if (len <= unitLen) {
			return str;
		}
		char[] chars = str.toCharArray();
		StringBuilder sb = new StringBuilder();
		for (int i = 0, j = i + 1; i < len; i++, j++) {
			sb.append(chars[i]);
			if (j % unitLen == 0 && j < len) {
				sb.append(sepChar);
			}
		}
		return sb.toString();
	}

	public static String seperate2(String str, int unitLen) {
		if (unitLen <= 0) {
			return str;
		}
		int len = str.length();
		if (len <= unitLen) {
			return str;
		}
		char[] chars = str.toCharArray();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < len; i++) {
			sb.append(chars[i]);
			// if (j % unitLen == 0 && j < len) {
			// sb.append(sepChar);
			// }
		}
		return sb.toString();
	}

	private static final String patternStartBlank = "^\\s+";
	private static final String patternEndBlank = "\\s+$";

	public static String trimStart(String str) {
		if (str == null) {
			return null;
		}
		return str.replaceFirst(patternStartBlank, "");
	}

	public static String trimEnd(String str) {
		if (str == null) {
			return null;
		}
		return str.replaceFirst(patternEndBlank, "");
	}

	private static final String patternSpaceChars = "\\s+";

	public static String removeAllSpaces(String str) {
		if (str == null) {
			return null;
		}
		return str.replaceAll(patternSpaceChars, StrUtil.EmptyStr);
	}

	public static String replaceAllSlashes(String srcStr, String repStr) {
		if (srcStr == null) {
			return null;
		}
		if (srcStr.indexOf(SlashStr) == -1 || SlashStr.equals(repStr)) {
			return srcStr;
		}
		StringBuilder sb = new StringBuilder();
		char[] srcChars = srcStr.toCharArray();
		for (int i = 0, j = srcChars.length; i < j; i++) {
			char tmpChar = srcChars[i];
			if (tmpChar == SlashChar) {
				sb.append(repStr);
			} else {
				sb.append(tmpChar);
			}
		}
		return sb.toString();
	}

	public static String replaceAllBackSlashes(String srcStr, String repStr) {
		if (srcStr == null) {
			return null;
		}
		if (srcStr.indexOf(BackSlashStr) == -1 || BackSlashStr.equals(repStr)) {
			return srcStr;
		}
		StringBuilder sb = new StringBuilder();
		char[] srcChars = srcStr.toCharArray();
		for (int i = 0, j = srcChars.length; i < j; i++) {
			char tmpChar = srcChars[i];
			if (tmpChar == BackSlashChar) {
				sb.append(repStr);
			} else {
				sb.append(tmpChar);
			}
		}
		return sb.toString();
	}

	// Content-Length, Content-Type, ETag, ETag: "9468c194abac81:24a31",
	// Last-Modified: Tue, 20 May 2008 07:21:19 GMT
	public static int MaxETagLength = 22;

	public static String generateETag(long lastModified, long fileSize) {
		String str1 = String.valueOf(lastModified);
		int len1 = str1.length();
		if (len1 >= MaxETagLength) {
			return str1.substring(len1 - MaxETagLength, len1);
		} else if (len1 == MaxETagLength - 1) {
			return str1 + "-";
		} else {
			String str2 = String.valueOf(fileSize);
			int len2 = str2.length();
			int lenx = MaxETagLength - len1 - 1;
			if (len2 < lenx) {
				str2 = StrUtil.leftPadding(str2, lenx, '0');
			} else {
				str2 = str2.substring(len2 - lenx, len2);
			}
			return str1 + "-" + str2;
		}
	}

	public static boolean strEqualsAny(String base, String... cmpStrs) {
		for (int i = 0; i < cmpStrs.length; i++) {
			if (base.equals(cmpStrs[i])) {
				return true;
			}
		}
		return false;
	}

	

	

	// 过滤表情符号
	private static String PatternEmoji = "[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]";

	public static String filterEmojiChars(String srcStr, String replacement) {
		if (srcStr != null) {
			srcStr = srcStr.replaceAll(PatternEmoji, replacement);
		}
		return srcStr;
	}

	public static String toJavaVariableName(String original) {
		return original.substring(0, 1).toLowerCase() + original.substring(1);
	}

	public static String toGetterMethodName(String fieldName) {
		return "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
	}

	public static String toGetterMethodNameForBoolean(String fieldName) {
		return "is" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
	}

	public static String toSetterMethodName(String fieldName) {
		return "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
	}
}
