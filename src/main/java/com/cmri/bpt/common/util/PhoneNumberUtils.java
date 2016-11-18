package com.cmri.bpt.common.util;

import java.util.Random;
import java.util.regex.Pattern;

public class PhoneNumberUtils {

	public static String[] PREFIX = { "132", "133", "134", "135", "136", "137", "138", "186", "187", "189" };

	public static String generateBlurPhoneNumber() {
		Random random = new Random();
		int nextInt = random.nextInt(10);
		return PREFIX[nextInt] + "XXXX" + CodeUtil.randomNumCode(4);
	}

	public static String generatePhoneNumber() {
		Random random = new Random();
		int nextInt = random.nextInt(10);
		return PREFIX[nextInt] + CodeUtil.randomNumCode(8);
	}

	private static Pattern PatternMobile = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$");

	/**
	 * 手机号验证
	 * 
	 * @param str
	 * @return 验证通过返回true
	 */
	public static boolean isMobile(String str) {
		return str != null && PatternMobile.matcher(str).matches();
	}

	private static Pattern PatternTelNo = Pattern
			.compile("(^([0][1-9][0-9]-?)?[0-9]{8}$)|(^([0][1-9]{3}-?)?[0-9]{7}$)");

	/**
	 * 固定电话号码验证
	 * 
	 * @param str
	 * @return 验证通过返回true
	 */
	public static boolean isTelNo(String str) {
		return str != null && PatternTelNo.matcher(str).matches();
	}

	public static boolean isPhoneNumber(String str) {
		return isMobile(str) || isTelNo(str);
	}

	public static String toReadablePhone(String phone) {
		if (phone == null) {
			return null;
		}
		//
		StringBuffer sb = new StringBuffer();
		if (isMobile(phone)) {
			sb.append(phone.substring(0, 3));
			sb.append(" ");
			sb.append(phone.substring(3, 7));
			sb.append(" ");
			sb.append(phone.substring(7));
		} else {
			sb.append(phone);
		}
		return sb.toString();
	}
}
