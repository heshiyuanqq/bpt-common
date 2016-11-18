package com.cmri.bpt.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang.time.DateUtils;



/**
 * @author Hu Changwei
 * @date 2013-06-11
 */
public class DateUtil {
	private DateUtil() {
		// prevent from being initialized
	}

	public static final SimpleDateFormat STD_DATE_FORMAT;
	public static final SimpleDateFormat STD_TIME_FORMAT;
	public static final SimpleDateFormat STD_DATE_TIME_FORMAT;
	public static final SimpleDateFormat STD_SHORT_DATE_TIME_FORMAT;
	public static final SimpleDateFormat STD_DATE_TIME_FORMATX;
	//
	public static final SimpleDateFormat FILE_DATE_TIME_FORMAT;
	public static final SimpleDateFormat INT_DATE_FORMAT;

	public static final String COMMON_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_FORMAT_PWD = "yyyyMMddHHmmss";
	public static final String SIMPLE_DATE = "yyyy-MM-dd";

	static {
		STD_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
		STD_TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");
		STD_DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		STD_SHORT_DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		STD_DATE_TIME_FORMATX = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		//
		FILE_DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd_HHmmss");
		INT_DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");
	}

	// 默认时区"GMT+08:00"
	public final static TimeZone defaultTimezone = TimeZone.getTimeZone("GMT+08:00");

	public static Calendar getCalendar(TimeZone timeZone) {
		if (timeZone == null) {
			timeZone = defaultTimezone;
		}
		return Calendar.getInstance(defaultTimezone);
	}

	public static Calendar getCalendar() {
		return getCalendar(null);
	}

	public static boolean isLeapYear(int year) {
		return year % 400 == 0 || year % 4 == 0 && year % 100 != 0;
	}

	public static Date get(int year, int month, int date) {
		return get(year, month, date, 0, 0, 0, 0);
	}

	public static Date get(int year, int month, int date, int hourOfDay, int minute) {
		return get(year, month, date, hourOfDay, minute, 0, 0);
	}

	public static Date get(int year, int month, int date, int hourOfDay, int minute, int second) {
		return get(year, month, date, hourOfDay, minute, second, 0);
	}

	public static Date get(int year, int month, int date, int hourOfDay, int minute, int second, int millSecond) {
		year = NumUtil.narrow(year, 0, 9999);
		month = NumUtil.narrow(month, 1, 12);
		switch (month) {
		case 2:
			date = isLeapYear(year) ? NumUtil.narrow(date, 1, 29) : NumUtil.narrow(date, 1, 28);
			break;
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			date = NumUtil.narrow(date, 1, 31);
			break;
		case 4:
		case 6:
		case 9:
		case 11:
			date = NumUtil.narrow(date, 1, 30);
			break;
		default:
		}
		hourOfDay = NumUtil.narrow(hourOfDay, 0, 23);
		minute = NumUtil.narrow(minute, 0, 59);
		second = NumUtil.narrow(second, 0, 59);
		millSecond = NumUtil.narrow(millSecond, 0, 999);
		//
		Calendar cal = getCalendar();
		month = month - 1;
		cal.set(year, month, date, hourOfDay, minute, second);
		cal.set(Calendar.MILLISECOND, millSecond);
		return cal.getTime();
	}

	public static String toStdDateStr(Date date) {
		return date == null ? null : STD_DATE_FORMAT.format(date);
	}

	public static String toStdTimeStr(Date date) {
		return date == null ? null : STD_TIME_FORMAT.format(date);
	}

	public static String toStdDateTimeStr(Date date) {
		return date == null ? null : STD_DATE_TIME_FORMAT.format(date);
	}

	public static String toStdShortDateTimeStr(Date date) {
		return date == null ? null : STD_SHORT_DATE_TIME_FORMAT.format(date);
	}

	public static String toStdDateTimeXStr(Date date) {
		return date == null ? null : STD_DATE_TIME_FORMATX.format(date);
	}

	public static String toFileDateTimeStr(Date date) {
		return date == null ? null : FILE_DATE_TIME_FORMAT.format(date);
	}

	public static String toFileDateTimeStr() {
		return toFileDateTimeStr(new Date());
	}

	public static Integer toInteger(Date date) {
		return date == null ? null : Integer.valueOf(INT_DATE_FORMAT.format(date));
	}

	public static Date fromStdDateTimeXStr(String dtStr) throws ParseException {
		try {
			return STD_DATE_TIME_FORMATX.parse(dtStr);
		} catch (Exception e) {
		}
		return null;
	}

	public static Date fromStdDateTimeStr(String dtStr) throws ParseException {
		try {
			return STD_DATE_TIME_FORMAT.parse(dtStr);
		} catch (Exception e) {
			dtStr = dtStr.substring(0, dtStr.length() - 4);
			try {
				return STD_DATE_FORMAT.parse(dtStr);
			} catch (Exception ex) {
			}
		}
		return null;
	}

	public static Date fromStdShortDateTimeStr(String dtStr) throws ParseException {
		return STD_SHORT_DATE_TIME_FORMAT.parse(dtStr);
	}

	public static Date fromStdDateStr(String dtStr) throws ParseException {
		return STD_DATE_FORMAT.parse(dtStr);
	}

	public static Date fromTimeStamp(String timestamp) {
		try {
			return new Date(Long.parseLong(timestamp));
		} catch (Exception ex) {
			return new Date();
		}
	}

	/**
	 * 日期计算
	 *
	 * @param refDate
	 * @param amount
	 * @return
	 */
	public static Date addYears(Date refDate, int amount) {
		return DateUtils.addYears(refDate, amount);
	}

	public static Date addYears(int amount) {
		Date refDate = new Date();
		return DateUtils.addYears(refDate, amount);
	}

	public static Date addMonths(Date refDate, int amount) {
		return DateUtils.addMonths(refDate, amount);
	}

	public static Date addMonths(int amount) {
		Date refDate = new Date();
		return DateUtils.addMonths(refDate, amount);
	}

	public static Date addDays(Date refDate, int amount) {
		return DateUtils.addDays(refDate, amount);
	}

	public static Date addDays(int amount) {
		Date refDate = new Date();
		return DateUtils.addDays(refDate, amount);
	}

	public static Date addHours(Date refDate, int amount) {
		return DateUtils.addHours(refDate, amount);
	}

	public static Date addHours(int amount) {
		Date refDate = new Date();
		return DateUtils.addHours(refDate, amount);
	}

	public static Date addMinutes(Date refDate, int amount) {
		return DateUtils.addMinutes(refDate, amount);
	}

	public static Date addMinutes(int amount) {
		Date refDate = new Date();
		return DateUtils.addMinutes(refDate, amount);
	}

	public static Date addSeconds(Date refDate, int amount) {
		return DateUtils.addSeconds(refDate, amount);
	}

	public static Date addSeconds(int amount) {
		Date refDate = new Date();
		return DateUtils.addSeconds(refDate, amount);
	}

	public static Date addWeeks(Date refDate, int amount) {
		return DateUtils.addWeeks(refDate, amount);
	}

	public static Date addWeeks(int amount) {
		Date refDate = new Date();
		return DateUtils.addWeeks(refDate, amount);
	}

	private static String msg = new String();

	/**
	 * 根据给定格式得到当前日期时间
	 * 
	 * @param fmt
	 *            需要的日期格式
	 * @return 符合格式要求的日期字符串 返回格式一般应为yyyy-MM-dd HH:mm:ss
	 */
	public static String getDate(String fmt) {
		Date myDate = new Date(System.currentTimeMillis());
		SimpleDateFormat sDateformat = new SimpleDateFormat(fmt);
		sDateformat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		return sDateformat.format(myDate).toString();
	}

	/**
	 * Definition: author: Tangww Created date: Jun 6, 2012
	 * 
	 * @return 符合格式要求的日期字符串 返回格式为yyyy-MM-dd HH:mm:ss
	 */
	public static String getCurrentDate() {
		Date myDate = new Date(System.currentTimeMillis());
		SimpleDateFormat sDateformat = new SimpleDateFormat(COMMON_DATE_FORMAT);
		sDateformat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		return sDateformat.format(myDate).toString();
	}

	/**
	 * 将指定日期格式化
	 * 
	 * @param fmt
	 * @param date
	 * @return
	 */
	public static String getDate(String fmt, Date date) {
		SimpleDateFormat sDateformat = new SimpleDateFormat(fmt);
		return sDateformat.format(date).toString();
	}

	public static String getDateByStr(String fromFmt, String toFmt, String dateStr) {
		try {
			SimpleDateFormat from = new SimpleDateFormat(fromFmt);
			SimpleDateFormat to = new SimpleDateFormat(toFmt);
			Date temp;
			temp = from.parse(dateStr);
			dateStr = to.format(temp);
			return dateStr;
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		return dateStr;
	}

	/**
	 * 将指定日期格式化
	 * 
	 * @param fmt
	 * @param date
	 * @return
	 */
	public static String getDateByStr(String fmt, String dateStr) {
		SimpleDateFormat sDateformat = new SimpleDateFormat(fmt);
		try {
			Date dates = sDateformat.parse(dateStr);
			return sDateformat.format(dates).toString();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dateStr;
	}

	public static String getCommonDateStr(Calendar calendar) {
		if (calendar == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(COMMON_DATE_FORMAT);
		return sdf.format(calendar.getTime());
	}

	public static Calendar getCommonDate(String dateStr) {
		try {
			return getCal(dateStr, COMMON_DATE_FORMAT);
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * 把给定的日期字符串转换成Calendar型
	 * 
	 * @param strdate
	 *            给定日期字符串
	 * @param fmt
	 *            给定日期字符串的格式
	 * @return 初始化好的Calendar类
	 * @exception ParseException
	 */

	public static Calendar getCal(String strdate, String fmt) {
		Calendar cal = null;
		try {
			// 判断给定参数是否为空，如果空则返回参数错误消息
			if (strdate == null || fmt == null) {
				msg = "Error: Method: DateTools.getCal :Incorrect para";
				String[] args = { msg };
			}
			// 根据给定日期格式得到当前时间
			SimpleDateFormat nowDate = new SimpleDateFormat(fmt);
			// 转换为格式为Date类型
			Date d = nowDate.parse(strdate, new java.text.ParsePosition(0));
			// 如果转换返回Date为空则抛出参数解析错误消息
			if (d == null) {
				msg = "Fatal: Method: DateTools.getCal :Incorrect Parse";
				String[] args = { msg };
			}
			// 得到一个Calendar实例
			cal = Calendar.getInstance();
			// Calendar日期归零
			cal.clear();
			// 设定当前时间
			cal.setTime(d);
		} catch (Exception e) { // 此处应该捕获ParseException，由于采用了ParsePosition(0)格式所以此异常不用捕获
			e.printStackTrace();

		}
		return cal;

	}

	/**
	 * 给定日期所在周是给定年的第几周
	 * 
	 * @param strdate
	 *            给定的日期字符串
	 * @param fmt
	 *            给定日期的格式
	 * @return 返回整型数字
	 * @exception CommonException
	 */
	public static int getWeekOfYear(String strdate, String fmt) throws Exception {
		// 返回值初始化，指定一个小于0的不可能得到的数字
		int ret = -1;
		try {
			// 判断给定参数是否为空，如果空则返回参数错误消息
			if (strdate == null || fmt == null) {
				msg = "Error: Method: DateTools.getWeekOfYear :Incorrect para";
				String[] args = { msg };
			}
			// 根据给定参数转换为Calendar类型
			Calendar cal = getCal(strdate, fmt);
			// 转换异常则返回错误消息
			if (cal == null) {
				msg = "Error: Method: DateTools.getWeekOfYear :Incorrect Calendar";
				String[] args = { msg };
			}
			// 得到给定日期所在周是给定年的第几周
			ret = cal.get(Calendar.WEEK_OF_YEAR);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;

	}

	/**
	 * 给定日期所在周的全部日期
	 * 
	 * @param strdate
	 *            给定的日期字符串
	 * @param oldfmt
	 *            给定日期的格式
	 * @param newfmt
	 *            返回日期的格式
	 * @return 从周一开始到周日的日期
	 * @exception CommonException
	 */
	@SuppressWarnings("static-access")
	public static String[] getWeekDay(String strdate, String oldfmt, String newfmt) {
		String weekday[] = new String[7];
		try {
			// 判断给定参数是否为空，如果空则返回参数错误消息
			if (strdate == null || oldfmt == null || newfmt == null) {
				msg = "Error: Method: DateTools.getWeekDay :Incorrect para";
				String[] args = { msg };
			}
			// 根据给定参数转换为Calendar类型
			Calendar cal = getCal(strdate, oldfmt);
			// 转换异常则返回错误消息
			if (cal == null) {
				msg = "Error: Method: DateTools.getWeekDay :Incorrect Calendar";
				String[] args = { msg };
			}
			// 得到给定日期是本周的的几天
			int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
			// 修改为中国习惯，从周一开始算一周时间
			cal.add(Calendar.DATE, -dayOfWeek + 2);
			// 根据参数设定返回格式
			SimpleDateFormat sdf = new SimpleDateFormat(newfmt);
			// 循环得到一周的时间
			weekday[0] = sdf.format(cal.getTime());
			for (int i = 1; i < 7; i++) {
				cal.add(Calendar.DATE, 1);
				weekday[i] = sdf.format(cal.getTime());
			}
		} catch (IndexOutOfBoundsException iobe) {
			iobe.printStackTrace();
		}
		return weekday;

	}

	/**
	 * 本方法完成得到给定周的全部日期
	 * 
	 * @param year
	 *            给定年
	 * @param week
	 *            给定周
	 * @param newfmt
	 *            返回日期的格式
	 * @return 从周一开始到周日的日期
	 */
	public static String[] getWeekDate(String year, int week, String newfmt) {
		String jweekday[] = new String[7];
		try {
			// 判断给定参数是否为空，如果空则返回参数错误消息
			if (year == null || year.length() < 4 || week <= 0 || newfmt == null) {
				msg = "Error: Method: DateTools.getWeekDate :Incorrect para";
				String[] args = { msg };
			}
			// 根据给定参数转换为Calendar类型,起始计算时间调整为当年的1月1日
			Calendar cal = getCal(year + "0101", "yyyyMMdd");
			// 转换异常则返回错误消息
			if (cal == null) {
				msg = "Error: Method: DateTools.getWeekDate :Incorrect Calendar";
				String[] args = { msg };

			}
			// java类的周计算从0开始，调整正常习惯为java描述需要减1
			week = week - 1;
			// 日期调整至当给定周的第一天
			cal.add(Calendar.DATE, week * 7 - cal.get(Calendar.DAY_OF_WEEK) + 2);
			// 根据参数设定返回格式
			SimpleDateFormat sdf = new SimpleDateFormat(newfmt);
			jweekday[0] = sdf.format(cal.getTime());
			// 循环得到一周的时间
			for (int i = 1; i < 7; i++) {
				cal.add(Calendar.DATE, 1);
				jweekday[i] = sdf.format(cal.getTime());
			}
		} catch (IndexOutOfBoundsException iobe) {
			iobe.printStackTrace();
		}
		return jweekday;

	}

	/**
	 * 本方法完成计算给定时间之前一段时间的日期时间
	 * 
	 * @param deftime
	 *            给定时间字符串
	 * @param timediff
	 *            以小时为单位
	 * @param oldfmt
	 *            给定时间的格式
	 * @param newfmt
	 *            返回时间的格式
	 * @return 时间字符串
	 */
	public static String getBeforeTime(String deftime, String oldfmt, int timediff, String newfmt) {
		String strBeforeTime = null;
		try {
			// 判断给定参数是否为空，如果空则返回参数错误消息
			if (deftime == null || deftime.equals("")) {
				msg = "Error: Method: DateTools.getBeforeTime :Incorrect para";
				String[] args = { msg };
			}
			// 根据给定参数转换为Calendar类型
			Calendar cal = getCal(deftime, oldfmt);
			// 转换异常则返回错误消息
			if (cal == null) {
				msg = "Error: Method: DateTools.getBeforeTime :Incorrect Calendar";
				String[] args = { msg };

			}
			// 以分钟计算之前的日期
			cal.add(Calendar.MINUTE, -timediff * 60);
			// 根据参数设定返回格式
			SimpleDateFormat sdf = new SimpleDateFormat(newfmt);
			// 格式化返回日期
			strBeforeTime = sdf.format(cal.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strBeforeTime;

	}

	/**
	 * 获取给定时间之后具体天数的时间
	 * 
	 * @param deftime
	 *            String 指定的时间
	 * @param oldfmt
	 *            String 指定的时间格式
	 * @param timediff
	 *            int 天数
	 * @param newfmt
	 *            String 指定的时间格式
	 * @return String
	 * @throws BaseException
	 */
	public static String getAfterTime(String deftime, String oldfmt, int timediff, String newfmt) {
		String strAfterTime = null;
		try {
			// 判断给定参数是否为空，如果空则返回参数错误消息
			if (deftime == null || deftime.equals("")) {
				msg = "Error: Method: DateTools.getBeforeTime :Incorrect para";
				String[] args = { msg };
			}
			// 根据给定参数转换为Calendar类型
			Calendar cal = getCal(deftime, oldfmt);
			// 转换异常则返回错误消息
			if (cal == null) {
				msg = "Error: Method: DateTools.getBeforeTime :Incorrect Calendar";
				String[] args = { msg };

			}
			// 以分钟计算之后的日期
			cal.add(Calendar.MINUTE, +timediff * 60);
			// 根据参数设定返回格式
			SimpleDateFormat sdf = new SimpleDateFormat(newfmt);
			// 格式化返回日期
			strAfterTime = sdf.format(cal.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strAfterTime;

	}

	/**
	 * 本方法完成把给定字符串转换成需要格式，如果不够位数则自动0补满位
	 * 
	 * @param mydate
	 *            给定时间
	 * @param oldfmt
	 *            给定时间格式
	 * @param newfmt
	 *            返回时间格式
	 * @return 字符串
	 */
	public static String fmtDate(String mydate, String oldfmt, String newfmt) {
		String restr = null;
		try {
			// 判断给定参数是否为空，如果空则返回参数错误消息
			if (mydate == null || oldfmt == null || newfmt == null) {
				msg = "Error: Method: DateTools.getBeforeTime :Incorrect para";
				String[] args = { msg };

			}
			SimpleDateFormat newDate = new SimpleDateFormat(newfmt);
			// 根据给定参数转换为Calendar类型
			Calendar cal = getCal(mydate, oldfmt);
			// 转换异常则返回错误消息
			if (cal == null) {
				msg = "Error: Method: DateTools.getBeforeTime :Incorrect Calendar";
				String[] args = { msg };
			}
			// 按给定格式返回
			restr = newDate.format(cal.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return restr;

	}

	// 判断当前时间是否在时间date之前
	// 时间格式 2005-4-21 16:16:34
	public static boolean isDateBefore(String date) {
		try {
			Date date1 = new Date();
			DateFormat df = DateFormat.getDateTimeInstance();
			return date1.before(df.parse(date));
		} catch (ParseException e) {
			return false;
		}
	}

	// 判断当前时间是否在时间date之前
	// 时间格式 2005-4-21 16:16:34
	public static boolean isDateBetween(String time, String startTime, String endTime) {
		try {
			DateFormat df = DateFormat.getDateTimeInstance();
			Date date1 = df.parse(time);
			if (date1.before(df.parse(endTime)) && date1.after(df.parse(startTime))) {
				return true;
			} else {
				return false;
			}
		} catch (ParseException e) {
			return false;
		}
	}

	/**
	 * 判断是否超时
	 * 
	 * @param beginDate
	 *            long
	 * @param TimeOutmillis
	 *            long
	 * @return boolean
	 */
	public static boolean whileUnitTimerOut(long beginDate, long TimeOutmillis) {
		long currentDate = System.currentTimeMillis();
		long endDate = beginDate + TimeOutmillis;
		return currentDate <= endDate;
	}

	/**
	 * 格式化时间
	 * 
	 * @param dateString
	 *            String
	 * @return Date
	 */
	public static Date formatDateString(String dateString) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date startDate = null;
		try {
			startDate = sdf.parse(dateString);
		} catch (ParseException ex) {
			startDate = null;
		}
		return startDate;
	}

	/*
	 * 判断当前的时间 密码是否过期。 pwdModDate 密码最后修改一次的时间 policyDate 密码策略的制定的天数
	 * 现在的时间在周期的后的时间返回true 密码过期
	 */
	public static Boolean pwdIsOverdue(String pwdModDate, String policyDate) {
		String expiredDateStr = "";
		int policy = Integer.parseInt(policyDate);
		try {
			expiredDateStr = DateUtil.getAfterTime(pwdModDate, "yyyy-MM-dd HH:mm:ss", policy * 24,
					"yyyy-MM-dd HH:mm:ss");
			Calendar expiredDate = DateUtil.getCal(expiredDateStr, "yyyy-MM-dd HH:mm:ss");
			String now = DateUtil.getDate("yyyy-MM-dd HH:mm:ss");
			Calendar nowcalendar = DateUtil.getCal(now, "yyyy-MM-dd HH:mm:ss");
			return nowcalendar.before(expiredDate); //
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 把字符串的时间转变成long型
	 * 
	 * @param str
	 *            字符串时间
	 * @param mode
	 *            字符串时间格式
	 * @return Date
	 */
	public static long parse(String str, String mode) {
		Date date = null;
		SimpleDateFormat formatter = new SimpleDateFormat(mode);
		try {
			date = formatter.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date.getTime();
	}

	/**
	 * 根据给定格式得到指定日期时间
	 * 
	 * @param fmt
	 *            需要的日期格式
	 * @param datelong
	 *            需要转换的long型时间
	 * @return
	 */
	public static String getLongbyDate(String fmt, long datelong) {
		Date myDate = new Date(datelong);
		SimpleDateFormat sDateformat = new SimpleDateFormat(fmt);
		// sDateformat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		return sDateformat.format(myDate).toString();
	}

	/**
	 * 根据日期得到周几
	 * 
	 * @param dt
	 * @return
	 */
	public static String getWeekOfDate(Date dt) {
		String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);

		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;

		return weekDays[w];
	}

	public static void main(String[] ss) {
		String dateStr = "2015-04-15 10:36:38";
		try {
			for (int i = 0; i < 10000; i++) {

				Date date = DateUtil.fromStdDateTimeStr(dateStr);
				// System.out.println(date);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//
		// try {
		// } catch (BaseException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// String time = System.currentTimeMillis()+"";
		// long l = Long.valueOf(time);
		// long ll = 5*60*60;
		// boolean b = whileUnitTimerOut(l,ll);
	}
}
