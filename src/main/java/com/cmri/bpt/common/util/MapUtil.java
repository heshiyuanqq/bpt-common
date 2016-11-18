package com.cmri.bpt.common.util;

import java.util.Map;

/**
 * Created by Administrator on 2015/5/1.
 */
public class MapUtil {

	public static Map<String, String> trimToEmpty(Map<String, String> map) {
		if (map == null || map.size() == 0) {
			return map;
		}
		for (Map.Entry<String, String> iter : map.entrySet()) {
			String value = iter.getValue();
			value = value == null ? "" : value.trim();
			value = (value.equalsIgnoreCase("undefined")) ? "" : value;
			iter.setValue(value);
		}
		return map;
	}

}
