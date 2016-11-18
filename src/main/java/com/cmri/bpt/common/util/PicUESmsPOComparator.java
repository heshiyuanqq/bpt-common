package com.cmri.bpt.common.util;

import java.util.Comparator;

import com.cmri.bpt.common.entity.PicUESmsPO;

/**
 * @author zzk
 * PicUESmsPO比较器，根据ue_location从小到大排序
 * */
public class PicUESmsPOComparator implements Comparator<PicUESmsPO> {

	@Override
	public int compare(PicUESmsPO o1, PicUESmsPO o2) {
		
		return o1.getUe_location().compareTo(o2.getUe_location());
	}

}
