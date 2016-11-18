package com.cmri.bpt.common.base;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;

/**
 * 按key的添加顺序保存数据，以后再修改不更改其顺序
 * 
 * @author koqiui
 * 
 * @param <K>
 * @param <V>
 */
public class OrdinalMap<K, V> implements Map<K, V> {
	Vector<K> innerKeys = new Vector<K>();
	TreeMap<K, V> innerMap = new TreeMap<K, V>(new Comparator<K>() {

		@Override
		public int compare(K key1, K key2) {
			return innerKeys.indexOf(key1) - innerKeys.indexOf(key2);
		}
	});

	@Override
	public int size() {
		return innerKeys.size();
	}

	@Override
	public boolean isEmpty() {
		return innerKeys.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return innerKeys.contains(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return innerMap.containsValue(value);
	}

	@Override
	public V get(Object key) {
		return innerMap.get(key);
	}

	@Override
	public V put(K key, V value) {
		if (!innerKeys.contains(key)) {
			innerKeys.add(key);
		}
		return innerMap.put(key, value);
	}

	@Override
	public V remove(Object key) {
		if (innerKeys.contains(key)) {
			innerKeys.remove(key);
		}
		return innerMap.remove(key);
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		for (Entry<? extends K, ? extends V> entry : m.entrySet()) {
			this.put(entry.getKey(), entry.getValue());
		}
	}

	@Override
	public void clear() {
		innerKeys.clear();
		innerMap.clear();
	}

	public Set<K> keySet() {
		return innerMap.keySet();
	}

	@Override
	public Collection<V> values() {
		return innerMap.values();
	}

	@Override
	public Set<Entry<K, V>> entrySet() {
		return innerMap.entrySet();
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 37 * result + innerKeys.hashCode();
		result = 37 * result + innerMap.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object another) {
		if (another == null) {
			return false;
		}
		if (this == another) {
			return true;
		}
		if (!(another instanceof OrdinalMap)) {
			return false;
		}
		OrdinalMap<?, ?> casted = (OrdinalMap<?, ?>) another;
		return this.innerKeys.equals(casted.innerKeys) && this.innerMap.equals(casted.innerMap);
	}

	@Override
	public String toString() {
		return innerMap.toString();
	}

}
