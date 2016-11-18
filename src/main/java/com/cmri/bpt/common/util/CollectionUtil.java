package com.cmri.bpt.common.util;

import java.util.ArrayList;
import java.util.List;

import com.cmri.bpt.common.base.Converter;
import com.cmri.bpt.common.base.TargetJudger;

public final class CollectionUtil {
	private CollectionUtil() {
		//
	}

	public static <T> T search(Iterable<T> base, TargetJudger<T> targetJudger) {
		for (T elem : base) {
			if (targetJudger.isTarget(elem)) {
				return elem;
			}
		}
		return null;
	}

	public static <T> int searchIndex(Iterable<T> base, TargetJudger<T> targetJudge) {
		int idx = -1;
		for (T elem : base) {
			idx++;
			if (targetJudge.isTarget(elem)) {
				return idx;
			}
		}
		return -1;
	}

	public static <TSrc, TDest> List<TDest> convertToList(Iterable<TSrc> src, Converter<TSrc, TDest> converter) {
		List<TDest> result = new ArrayList<TDest>();
		int idx = -1;
		for (TSrc elem : src) {
			idx++;
			result.add(converter.convert(elem, idx));
		}
		return result;
	}
}
