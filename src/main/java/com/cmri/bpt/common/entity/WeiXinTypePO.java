package com.cmri.bpt.common.entity;

import java.io.Serializable;

public class WeiXinTypePO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String case_name; //case名称
	private String type; //微信的类型
	public String getCase_name() {
		return case_name;
	}
	public void setCase_name(String case_name) {
		this.case_name = case_name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
