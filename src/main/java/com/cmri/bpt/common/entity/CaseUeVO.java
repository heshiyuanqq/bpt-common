package com.cmri.bpt.common.entity;

import java.io.Serializable;

public class CaseUeVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String casename;//测试用例的ID
	private String ue_id;//手机的id
	private int user_id;//用户的id
	public String getCasename() {
		return casename;
	}
	public void setCasename(String casename) {
		this.casename = casename;
	}

	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getUe_id() {
		return ue_id;
	}
	public void setUe_id(String ue_id) {
		this.ue_id = ue_id;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

}
