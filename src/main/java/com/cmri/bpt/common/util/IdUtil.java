package com.cmri.bpt.common.util;

import com.cmri.bpt.common.alg.IndexedIdCodecoder;
import com.cmri.bpt.common.alg.PairDiffIdCodecoder;
import com.cmri.bpt.common.base.IdCodecoder;
import com.cmri.bpt.common.exception.ValidationException;

/**
 * 
 * @author koqiui
 * 
 */
public final class IdUtil {
	private IdUtil() {
		//
	}

	//
	private static final IdCodecoder<Integer> ID_CODECODER_PAIR_DIFF = new PairDiffIdCodecoder();
	@SuppressWarnings("unused")
	private static final IdCodecoder<Integer> ID_CODECODER_INDEXED = new IndexedIdCodecoder();
	// 当前编码、解码器
	private static final IdCodecoder<Integer> CURRENT_ID_CODECODER = ID_CODECODER_PAIR_DIFF;
	// 是否启用验证
	private static final Boolean ENABLE_VERIFICATION = true;

	// 默认
	public static String encode(Integer id) {
		String encoded = CURRENT_ID_CODECODER.encode(id);
		if (ENABLE_VERIFICATION) {
			return encoded + "-" + Integer.toString(id, 10);
		} else {
			return encoded;
		}
	}

	public static Integer decode(String encodedId) {
		if (ENABLE_VERIFICATION) {
			int hpIndex = encodedId.lastIndexOf("-");
			if (hpIndex == -1) {
				throw new ValidationException("无效id编码");
			}
			String encoded = encodedId.substring(0, hpIndex);
			Integer idToCheck = null;
			try {
				idToCheck = Integer.parseInt(encodedId.substring(hpIndex + 1), 10);
			} catch (Exception ex) {
				throw new ValidationException("无效id编码");
			}
			//
			Integer id = null;
			try {
				id = CURRENT_ID_CODECODER.decode(encoded);
			} catch (NumberFormatException ne) {
				throw new ValidationException("无效id编码");
			}
			if (id.equals(idToCheck)) {
				return id;
			} else {
				throw new ValidationException("无效id编码");
			}
		} else {
			return CURRENT_ID_CODECODER.decode(encodedId);
		}
	}

}
