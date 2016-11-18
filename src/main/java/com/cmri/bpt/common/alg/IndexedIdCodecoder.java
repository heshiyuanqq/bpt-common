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
 *         2、对字符串每一位字符（大写后）生成索引位置的两位（前补0的）字符串；<br/>
 *         3、对字符索引位置两位（前补0的）的字符串中间插入一位随机字符（形成3位）；<br/>
 *         4、拼接所有字符（3位）索引位置字符串生成最终字符串<br/>
 * 
 *         解码：顺序相反<br/>
 * 
 * 
 */
public class IndexedIdCodecoder implements IdCodecoder<Integer> {
	private static final int RADIX_RAW = 20;
	//
	private static final char[] ALPHABET_ARRAY = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
			'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
			'W', 'X', 'Y', 'Z' };
	private static final int ALPHABET_ARRAY_LENGTH = ALPHABET_ARRAY.length;
	//
	private static final char PADDIND_ZERO = '0';
	//
	private static final Random INT_RANDOM = NumUtil.getIntRandom();

	//
	private static String generateCharPairFor(char xChar) {
		int index = NumUtil.getRandomInt(INT_RANDOM, 0, ALPHABET_ARRAY_LENGTH - 1);
		char randomChar = ALPHABET_ARRAY[index];
		for (int i = 0; i < ALPHABET_ARRAY_LENGTH; i++) {
			if (ALPHABET_ARRAY[i] == xChar) {
				char[] indexChars = StrUtil.leftPadding(Integer.toString(i, 10), 2, PADDIND_ZERO).toCharArray();
				return String.valueOf(indexChars[0]) + String.valueOf(randomChar) + String.valueOf(indexChars[1]);
			}
		}
		return null;
	}

	private static char parseCharForCharPair(String charPair) {
		char[] chars = charPair.toCharArray();
		String indexChars = String.valueOf(chars[0]) + String.valueOf(chars[2]);
		int index = Integer.valueOf(indexChars, 10);
		return ALPHABET_ARRAY[index];
	}

	@Override
	public String encode(Integer id) {
		String charStr = Integer.toString(id, RADIX_RAW).toUpperCase();
		//
		// System.out.println(charStr);
		//
		StringBuilder sb = new StringBuilder("");
		for (int i = 0, len = charStr.length(); i < len; i++) {
			char xChar = charStr.charAt(i);
			String charPair = generateCharPairFor(xChar);
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
			String charPair = encodedId.substring(i * 3, (i + 1) * 3);
			char xChar = parseCharForCharPair(charPair);
			sb.append(xChar);
		}
		String charStr = sb.toString();
		//
		// System.out.println(charStr);
		//
		return Integer.valueOf(charStr, RADIX_RAW);
	}

}
