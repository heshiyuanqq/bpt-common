package com.cmri.bpt.common.entity;

import java.io.Serializable;

public class FtpFailPicturePO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int x_data;
	private int y_data;
	public int getX_data() {
		return x_data;
	}
	public void setX_data(int x_data) {
		this.x_data = x_data;
	}
	public int getY_data() {
		return y_data;
	}
	public void setY_data(int y_data) {
		this.y_data = y_data;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

}
