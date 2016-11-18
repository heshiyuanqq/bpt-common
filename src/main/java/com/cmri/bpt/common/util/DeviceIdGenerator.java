package com.cmri.bpt.common.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;

public class DeviceIdGenerator {

	private static Map<Integer, AtomicInteger> idHolder = new HashMap<Integer, AtomicInteger>();

	public static synchronized Integer getId(Integer id) {

		AtomicInteger autoI = idHolder.get(id);
		if (autoI == null) {
			autoI = new AtomicInteger(0);
			idHolder.put(id, autoI);
		}

		return autoI.incrementAndGet();
	}

	public static synchronized void init(Map<Integer, Integer> maps) {

		idHolder = new HashMap<Integer, AtomicInteger>();
		for (Entry<Integer, Integer> entry : maps.entrySet()) {

			idHolder.put(entry.getKey(), new AtomicInteger(entry.getValue()));
		}

	}

	public static synchronized void reset(Integer id) {
		AtomicInteger autoI = new AtomicInteger(0);
		idHolder.put(id, autoI);
	}

}
