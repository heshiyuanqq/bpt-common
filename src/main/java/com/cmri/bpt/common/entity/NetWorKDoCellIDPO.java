package com.cmri.bpt.common.entity;

import java.io.Serializable;

public class NetWorKDoCellIDPO  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String cellid;
	private String cellname;
	public String getCellid() {
		return cellid;
	}
	public void setCellid(String cellid) {
		this.cellid = cellid;
	}
	public String getCellname() {
		return cellname;
	}
	public void setCellname(String cellname) {
		this.cellname = cellname;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

}
