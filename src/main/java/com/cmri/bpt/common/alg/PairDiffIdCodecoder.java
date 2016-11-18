package com.cmri.bpt.common.alg;

import java.util.Random;

import com.cmri.bpt.common.base.IdCodecoder;
import com.cmri.bpt.common.util.NumUtil;
import com.cmri.bpt.common.util.StrUtil;

/**
 * 
 * @author koqiui <br/>
 *         算法描述： <br/>
 *         编码： 1、把给定的整数转成给定的 RADIX_RAW 进制的字符串；<br/>
 *         2、对字符串每一位字符按 RADIX_VALUE 进制取值；<br/>
 *         3、对字符的取值结果生成随机的英文字母对，要求英文字母之间的差值为字符的取值（中间插入一个随机字符形成3位）；<br/>
 *         4、拼接所有字符对生成最终字符串<br/>
 * 
 *         解码：顺序相反<br/>
 * 
 * 
 */
public class PairDiffIdCodecoder implements IdCodecoder<Integer> {
	private static final int RADIX_RAW = 13;
	private static final int RADIX_VALUE = 17;
	//
	private static final char[] ALPHABET_ARRAY = new char[] { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
			'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
	private static final int ALPHABET_ARRAY_LENGTH = ALPHABET_ARRAY.length;
	private static final char ALPHABET_MIN = ALPHABET_ARRAY[0];
	private static final char ALPHABET_MAX = ALPHABET_ARRAY[ALPHABET_ARRAY_LENGTH - 1];
	//
	private static final Random INT_RANDOM = NumUtil.getIntRandom();

	//
	private static String generateCharPairFor(int radixVal) {
		while (true) {
			int index = NumUtil.getRandomInt(INT_RANDOM, 0, ALPHABET_ARRAY_LENGTH - 1);
			char lChar = ALPHABET_ARRAY[index];
			int rCharVal = lChar - radixVal;
			if (rCharVal >= ALPHABET_MIN && rCharVal <= ALPHABET_MAX) {
				index = NumUtil.getRandomInt(INT_RANDOM, 0, ALPHABET_ARRAY_LENGTH - 1);
				char randomChar = ALPHABET_ARRAY[index];
				//
				char rChar = (char) rCharVal;
				return String.valueOf(lChar) + String.valueOf(randomChar) + String.valueOf(rChar);
			}
		}
	}

	@Override
	public String encode(Integer id) {
		String charStr = Integer.toString(id, RADIX_RAW);
		//
		// System.out.println(charStr);
		//
		StringBuilder sb = new StringBuilder("");
		for (int i = 0, len = charStr.length(); i < len; i++) {
			char xChar = charStr.charAt(i);
			int radixVal = Integer.valueOf(String.valueOf(xChar), RADIX_VALUE);
			String charPair = generateCharPairFor(radixVal);
			sb.append(charPair);
		}
		return sb.toString();
	}

	@Override
	public Integer decode(String encodedId) {
		if (StrUtil.isNullOrBlank(encodedId)) {
			return null;
		}
		encodedId = encodedId.trim();
		int len = encodedId.length();
		if (len % 3 != 0) {
			return null;
		}
		int pairLen = len / 3;
		StringBuilder sb = new StringBuilder("");
		for (int i = 0; i < pairLen; i++) {
			String pair = encodedId.substring(i * 3, (i + 1) * 3);
			char[] charPair = pair.toCharArray();
			int radixVal = charPair[0] - charPair[2];
			String xChar = Integer.toString(radixVal, RADIX_VALUE);
			sb.append(xChar);
		}
		String charStr = sb.toString();
		//
		// System.out.println(charStr);
		//
		return Integer.valueOf(charStr, RADIX_RAW);
	}
}
