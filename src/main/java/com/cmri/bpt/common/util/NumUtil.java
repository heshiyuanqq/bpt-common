package com.cmri.bpt.common.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Random;

/**
 * 
 * @author Hu Changwei
 * @date 2013-12-16
 * 
 */
public class NumUtil {
	public static final DecimalFormat Fraction2Format;

	static {
		Fraction2Format = new DecimalFormat("#.##");
	}

	private NumUtil() {
		// prevent from being initialized
	}

	public static Random getIntRandom(long seed) {
		return new Random(seed);
	}

	public static Random getIntRandom() {
		return new Random();
	}

	public static int getRandomInt(Random random, int min, int max) {
		return min + random.nextInt(max + 1 - min);
	}

	public static int getRandomInt(Random random, int max) {
		return getRandomInt(random, 1, max);
	}

	public static int narrow(int num, int min, int max) {
		return Math.max(min, Math.min(num, max));
	}

	public static Integer parseInteger(String numStr) {
		if (!StrUtil.hasText(numStr)) {
			return null;
		}
		int idx = numStr.indexOf(".");
		return idx == -1 ? Integer.parseInt(numStr) : Integer.parseInt(numStr.substring(0, idx));
	}

	public static String trimMoneyToYuanOrFen(Double money) {
		if (money == null) {
			return StrUtil.EmptyStr;
		}
		String str = money.toString();
		int dotIndex = str.indexOf(".");
		if (dotIndex != -1) {
			boolean hasFen = false;
			for (int i = dotIndex + 1; i <= dotIndex + 2 && i < str.length(); i++) {
				if (str.charAt(i) != '0') {
					hasFen = true;
					break;
				}
			}
			if (hasFen) {
				String fen = str.substring(dotIndex + 1);
				fen = StrUtil.rightPadding(fen, 2, '0');
				fen = fen.substring(0, 2);
				str = str.substring(0, dotIndex) + "." + fen;
			} else {
				str = str.substring(0, dotIndex);
			}
		}
		return str;
	}

	public static boolean areEqual(BigDecimal num1, BigDecimal num2) {
		if (num1 == null) {
			return num2 == null;
		} else {
			return num1.compareTo(num2) == 0;
		}
	}

	public static boolean areEqual(Integer num1, Integer num2) {
		if (num1 == null) {
			return num2 == null;
		} else {
			return num1.compareTo(num2) == 0;
		}
	}

	public static int ceil(int devided, int devider) {
		int intResult = devided / devider;
		int test = intResult * devider;
		return test < devided ? intResult + 1 : intResult;
	}

	public static Double roundTo2(Double value) {
		return roundTo(value, 2);
	}

	public static Double roundTo(Double value, int decimals) {
		NumberFormat format = NumberFormat.getInstance();
		format.setMaximumFractionDigits(decimals);
		format.setGroupingUsed(false);
		return Double.valueOf(format.format(value));
	}

	public static String roundToString(Double value, int decimals) {
		Double value2 = roundTo(value, decimals);
		return String.format("%." + decimals + "f", value2);
	}

}
