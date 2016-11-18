package com.cmri.bpt.common.util;

import java.util.Map;
import java.util.TreeMap;

import com.cmri.bpt.common.entity.CallLogPO;
import com.cmri.bpt.common.entity.CellChangePicturePO;
import com.cmri.bpt.common.entity.WebPicturePO;
import com.cmri.bpt.common.entity.WeiXinPicturePO;



public class sortMapbykey {
	/**
	 * @author 范晓文
	 * 将map按照key值从小到大排序
	 * @param map
	 * @return
	 */
	public static Map<Integer,CallLogPO> sortMapByKey(Map<Integer,CallLogPO> map) {
	    if (map == null || map.isEmpty()) {
	        return null;
	    }

	    Map<Integer,CallLogPO> sortMap = new TreeMap<Integer,CallLogPO>(
	           new MapKeyComparator());

	    sortMap.putAll(map);

	    return sortMap;
	}

	public static Map<Integer,WebPicturePO> sortWebMapByKey(Map<Integer,WebPicturePO> map) {
	    if (map == null || map.isEmpty()) {
	        return null;
	    }

	    Map<Integer,WebPicturePO> sortMap = new TreeMap<Integer,WebPicturePO>(
	           new MapKeyComparator());

	    sortMap.putAll(map);

	    return sortMap;
	}

	public static Map<Integer,WeiXinPicturePO> sortWeiXinMapByKey(Map<Integer,WeiXinPicturePO> map) {
	    if (map == null || map.isEmpty()) {
	        return null;
	    }

	    Map<Integer,WeiXinPicturePO> sortMap = new TreeMap<Integer,WeiXinPicturePO>(
	           new MapKeyComparator());

	    sortMap.putAll(map);

	    return sortMap;
	}



	public static Map<Integer,CellChangePicturePO> sortCellChangeMapByKey(Map<Integer,CellChangePicturePO> map) {
	    if (map == null || map.isEmpty()) {
	        return null;
	    }

	    Map<Integer,CellChangePicturePO> sortMap = new TreeMap<Integer,CellChangePicturePO>(
	           new MapKeyComparator());

	    sortMap.putAll(map);

	    return sortMap;
	}
	public static Map<Integer,Integer> sortMapByKey1(Map<Integer,Integer> map) {
	    if (map == null || map.isEmpty()) {
	        return null;
	    }

	    Map<Integer,Integer> sortMap = new TreeMap<Integer,Integer>(
	           new MapKeyComparator());

	    sortMap.putAll(map);


	    return sortMap;
	}


}
