package com.cmri.bpt.common.entity;

import java.io.Serializable;

public class FileNamesPO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String filename;
	private long creattime;
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public long getCreattime() {
		return creattime;
	}
	public void setCreattime(long creattime) {
		this.creattime = creattime;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

}
